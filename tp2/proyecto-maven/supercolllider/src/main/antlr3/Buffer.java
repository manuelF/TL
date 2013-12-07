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

}


