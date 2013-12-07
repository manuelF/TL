import java.util.ArrayList;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;


public class Buffer {
    public static int sampling_rate = 44100;
    public static int beat = sampling_rate / 12;

	public static ArrayList<Double> buffer(double number) {
	    ArrayList<Double> buff = new ArrayList<Double>();
        for (int i = 0; i < beat; i++){
            buff.add(number);
        }
        return buff;
	}

    public static ArrayList<Double> sin (double c, double a) {
        ArrayList<Double> buff = new ArrayList<Double>();
        double x = (c * 2 * Math.PI)/beat;

        for (int i = 0; i < beat; i++){
            buff.add(a * Math.sin(i * x));
        }
        return buff;
    }

    public static ArrayList<Double> sil() {
        ArrayList<Double> buff = new ArrayList<Double>();
        double cero = 0;
        for (int i = 0; i < beat; i++){
            buff.add(cero);
        }
        return buff;
    }

    public static ArrayList<Double> lin (double a, double b) {
        ArrayList<Double> buff = new ArrayList<Double>();
        double diff = (b - a)/(beat - 1);
        for (int i = 0; i < beat; i++){
            buff.add(a + diff * i);
        }
        return buff;
    }

    public static ArrayList<Double> noi (double a) {
        ArrayList<Double> buff = new ArrayList<Double>();
        for (int i = 0; i < beat*a; i++){
            buff.add(Math.random()*2 - 1);
        }
        return buff;
    }

    public static ArrayList<Double> play (ArrayList<Double> buff) {
        return buff;
    }

    public static void music_play(ArrayList<Double> notes) {
        byte[] buf = new byte[1];
        double sampling_freq =sampling_rate;
        int duration = beat;

        try {
            AudioFormat af = new AudioFormat((float) sampling_freq, 8, 1, true, false);
            SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
            sdl = AudioSystem.getSourceDataLine(af);
            sdl.open(af);
            sdl.start();
            System.out.println("Buffer to Play size size: " + notes.size());
            System.out.println("Sound duration: " + (1/12.0)* ((double)notes.size())/duration+ "s. ");

            for(int j = 0 ; j < notes.size(); j+=duration){

                for (int i = 0; i < duration; i++) {
                    Double note = notes.get(j+i);
                    double angle = i / ( note) * 2.0 * Math.PI;
                    buf[0] = (byte) (Math.sin(angle) * 100);
                    sdl.write(buf, 0, 1);
                }
            }
            sdl.drain();
            sdl.stop();
            sdl.close();
        } catch (Exception ex) {
            System.out.println("fin del mundo " + ex.getMessage());
        }
    }
    public static ArrayList<Double> Concatenate(
            ArrayList<Double> l,
            ArrayList<Double> r ){

        ArrayList<Double> ret = new ArrayList<Double>();
        ret.addAll(l);
        ret.addAll(r);
        ret.trimToSize();
        return ret;
    
    }

    public static ArrayList<Double> resample(ArrayList<Double> buff, int L){
    	ArrayList<Double> r = new ArrayList<Double>();
    	int size = buff.size();
    	for (int i = 0; i < L; i++){
    		r.add(buff.get(i * size / L));
    	}
    	return r;
    }
    
    public static ArrayList<Double> tune(ArrayList<Double> buff, int P){
    	int size = buff.size();
    	return resample(buff,Utils.doubleToInt(size * Math.pow(2.0, -P/12)));
    }
    
    public static ArrayList<Double> resize(ArrayList<Double> buff, int L){
    	ArrayList<Double> r = new ArrayList<Double>();
    	int size = buff.size();
    	for (int i = 0; i < L; i++){
    		r.add(buff.get(i % size));
    	}
    	return r;
    }
    
    public static ArrayList<Double> reduce(ArrayList<Double> buff, int N){
    	int L = beat * N;
    	if(buff.size() > L){
    		return resample(buff, L);
    	} else {
    		return buff;
    	}
    }
    
    public static ArrayList<Double> expand(ArrayList<Double> buff, int N){
    	int L = beat * N;
    	if(buff.size() < L){
    		return resample(buff, L);
    	} else {
    		return buff;
    	}
    }
    
    public static ArrayList<Double> fill(ArrayList<Double> buff, int N){
    	ArrayList<Double> r = new ArrayList<Double>();
    	int L = beat * N;
    	int size = buff.size(); 
    	for (int i = 0; i < L; i++){
    		if(i < size){
    			r.add(buff.get(i));
    		} else {
    			r.add(0.0);
    		}
    	}
    	
    	return r;
    }
    
    public static ArrayList<Double> oper(String op, ArrayList<Double> buffA, ArrayList<Double> buffB){
		ArrayList<Double> a;
		ArrayList<Double> b;
		if (buffA.size() == 1  || buffB.size() == 1){
			a = buffA;
			b = buffB;
		} else {
			if (buffA.size() < buffB.size()){
	    		a = resize(buffA,  buffB.size());
	    		b = buffB;
	    	} else {
	    		a = buffA;
	    		b = resize(buffB,  buffA.size());
	    	}
		}
    	
    	
    	if (op.equals("+")) return sum(a,b);
    	if (op.equals("-")) return sub(a,b);
    	if (op.equals("*")) return mul(a,b);
    	if (op.equals("/")) return div(a,b);
    	if (op.equals("m")) return mix(a,b);
    	return null;
    	
    }
    
    public static ArrayList<Double> sum(ArrayList<Double> a, ArrayList<Double> b){
    	ArrayList<Double> r = new ArrayList<Double>();
    	if (a.size() == b.size()){
    		for (int i = 0; i < a.size(); i++) r.add(a.get(i) + b.get(i));
    	} else if (a.size() == 1 || b.size() == 1){
    		for (int i = 0; i < b.size(); i++) r.add(a.get(0) + b.get(i));
    	} 
    	
    	return r;
    }
    
    public static ArrayList<Double> sub(ArrayList<Double> a, ArrayList<Double> b){
    	ArrayList<Double> r = new ArrayList<Double>();
    	for (int i = 0; i < a.size(); i++) r.add(a.get(i) - b.get(i));
    	return r;
    }
    
    public static ArrayList<Double> mul(ArrayList<Double> a, ArrayList<Double> b){
    	ArrayList<Double> r = new ArrayList<Double>();
    	if (a.size() == b.size()){
    		for (int i = 0; i < a.size(); i++) r.add(a.get(i) * b.get(i));
    	} else if (a.size() == 1 || b.size() == 1){
    		for (int i = 0; i < b.size(); i++) r.add(a.get(0) * b.get(i));
    	} 
    	return r;
    }
    
    public static ArrayList<Double> div(ArrayList<Double> a, ArrayList<Double> b){
    	ArrayList<Double> r = new ArrayList<Double>();
    	for (int i = 0; i < a.size(); i++) r.add(a.get(i) / b.get(i));
    	return r;
    }
    
    public static ArrayList<Double> mix(ArrayList<Double> a, ArrayList<Double> b){
    	ArrayList<Double> r = new ArrayList<Double>();
    	for (int i = 0; i < a.size(); i++) r.add((a.get(i) + b.get(i))/2);
    	return r;
    }

}


