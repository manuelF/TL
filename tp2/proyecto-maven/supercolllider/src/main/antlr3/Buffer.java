import java.util.ArrayList;
import java.lang.Math;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;


public class Buffer {
    public static int sampling_rate = 44100;
    public static int beat = sampling_rate / 12;

    public static ArrayList<Double> resize(ArrayList<Double> a, double number) {
        ArrayList<Double> buff = new ArrayList<Double>();
        for (int i = 0; i < number; i++) {
            buff.add(a.get(i % a.size()));
        }
        return buff;
    }

    public static ArrayList<Double> buffer(double number) {
        ArrayList<Double> buff = new ArrayList<Double>();
        for (int i = 0; i < beat; i++) {
            buff.add(number);
        }
        return buff;
    }

    public static ArrayList<Double> sin (double c, double a) {
        ArrayList<Double> buff = new ArrayList<Double>();
        double x = (c * 2 * Math.PI)/beat;

        for (int i = 0; i < beat; i++) {
            buff.add(a * Math.sin(i * x));
        }
        return buff;
    }

    public static ArrayList<Double> sil() {
        ArrayList<Double> buff = new ArrayList<Double>();
        double cero = 0;
        for (int i = 0; i < beat; i++) {
            buff.add(cero);
        }
        return buff;
    }

    public static ArrayList<Double> lin (double a, double b) {
        ArrayList<Double> buff = new ArrayList<Double>();
        double diff = (b - a)/(beat - 1);
        for (int i = 0; i < beat; i++) {
            buff.add(a + diff * i);
        }
        return buff;
    }

    public static ArrayList<Double> noi (double a) {
        ArrayList<Double> buff = new ArrayList<Double>();
        for (int i = 0; i < beat*a; i++) {
            buff.add(Math.random()*2 - 1);
        }
        return buff;
    }

    public static void music_play(ArrayList<Double> notes, Double speed) {
        byte[] buf = new byte[1];
        double sampling_freq =sampling_rate;

        try {
            AudioFormat af = new AudioFormat((float) sampling_freq, 8, 1, true, false);
            SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
            sdl = AudioSystem.getSourceDataLine(af);
            sdl.open(af);
            sdl.start();
            System.out.println("Buffer to Play size size: " + notes.size());
            System.out.println("Sound duration: " + (1/12.0)* (((double)notes.size())/beat)/speed+ "s. ");
            assert(speed>0.0);
            double true_notes_length = notes.size()/speed;

            for(int j = 0 ; j < true_notes_length; j++) {
                Double note;
                int index_base = (int) ((double)j*speed);
                int index_top = (int) (((double)(j+1))*speed);
                if(index_base>=notes.size()) index_base=notes.size()-1;
                if(index_top>=notes.size()) index_top=notes.size()-1;
                Double note_left = notes.get(index_base)*100.0;
                Double note_right = notes.get(index_top)*100.0;
                Double coef = (index_base-index_top)/(note_left-note_right) ;
                note = coef * note_left + (1.0-coef) * note_right;
                buf[0]= note.byteValue();
                sdl.write(buf, 0, 1);

            }
            sdl.drain();
            sdl.stop();
            sdl.close();
        } catch (Exception ex) {
            System.out.println("Error en la reproduccion: " + ex.getCause() + " \n Detalles: "  + ex.getMessage());
        }
    }

    public static ArrayList<Double> resample(ArrayList<Double> buff, int L) {
        ArrayList<Double> r = new ArrayList<Double>();
        int size = buff.size();
        for (int i = 0; i < L; i++) {
            r.add(buff.get(i * size / L));
        }
        return r;
    }

    public static ArrayList<Double> tune(ArrayList<Double> buff, int P) {
        int size = buff.size();
        return resample(buff,Utils.doubleToInt(size * Math.pow(2.0, -P/12.0)));
    }

    public static ArrayList<Double> resize(ArrayList<Double> buff, int L) {
        ArrayList<Double> r = new ArrayList<Double>();
        int size = buff.size();
        for (int i = 0; i < L; i++) {
            r.add(buff.get(i % size));
        }
        return r;
    }

    public static ArrayList<Double> reduce(ArrayList<Double> buff, int N) {
        int L = beat * N;
        if(buff.size() > L) {
            return resample(buff, L);
        } else {
            return buff;
        }
    }

    public static ArrayList<Double> expand(ArrayList<Double> buff, int N) {
        int L = beat * N;
        if(buff.size() < L) {
            return resample(buff, L);
        } else {
            return buff;
        }
    }

    public static ArrayList<Double> fill(ArrayList<Double> buff, int N) {
        ArrayList<Double> r = new ArrayList<Double>();
        int L = beat * N;
        int size = buff.size();
        for (int i = 0; i < L; i++) {
            if(i < size) {
                r.add(buff.get(i));
            } else {
                r.add(0.0);
            }
        }

        return r;
    }

    public static ArrayList<Double> oper(String op, ArrayList<Double> buffA, ArrayList<Double> buffB) {
        ArrayList<Double> a = buffA.size() < buffB.size() ? resize(buffA, buffB.size()) : buffA;
        ArrayList<Double> b = buffB.size() < buffA.size() ? resize(buffB, buffA.size()) : buffB;

        if (op.equals("+")) return sum(a,b);
        if (op.equals("-")) return sub(a,b);
        if (op.equals("*")) return mul(a,b);
        if (op.equals("/")) return div(a,b);
        if (op.equals("m")) return mix(a,b);
        return null;
    }

    public static ArrayList<Double> sum(ArrayList<Double> a, ArrayList<Double> b) {
        ArrayList<Double> r = new ArrayList<Double>();
        for (int i = 0; i < a.size(); i++) r.add(a.get(i) + b.get(i));
        return r;
    }

    public static ArrayList<Double> sub(ArrayList<Double> a, ArrayList<Double> b) {
        ArrayList<Double> r = new ArrayList<Double>();
        for (int i = 0; i < a.size(); i++) r.add(a.get(i) - b.get(i));
        return r;
    }

    public static ArrayList<Double> mul(ArrayList<Double> a, ArrayList<Double> b) {
        ArrayList<Double> r = new ArrayList<Double>();
        for (int i = 0; i < a.size(); i++) r.add(a.get(i) * b.get(i));
        return r;
    }

    public static ArrayList<Double> div(ArrayList<Double> a, ArrayList<Double> b) {
        ArrayList<Double> r = new ArrayList<Double>();
        for (int i = 0; i < a.size(); i++) r.add(a.get(i) / b.get(i));
        return r;
    }

    public static ArrayList<Double> mix(ArrayList<Double> a, ArrayList<Double> b) {
        ArrayList<Double> r = new ArrayList<Double>();
        for (int i = 0; i < a.size(); i++) r.add((a.get(i) + b.get(i))/2);
        return r;
    }

    public static ArrayList<Double> expand(ArrayList<Double> buff, Double n) {
        Double total = beat*n;
        if (buff.size() < total) {
            return resize(buff, total);
        } else {
            return buff;
        }
    }

    public static ArrayList<Double> reduce(ArrayList<Double> buff, Double n) {
        Double total = beat*n;
        if (buff.size() > total) {
            return resize(buff, total);
        } else {
            return buff;
        }
    }

    public static ArrayList<Double> post(ArrayList<Double> buff) {
        for(int i = 0; i<buff.size(); i += beat) {
            System.out.print(buff.get(i + beat/2) + " ");
        }
        System.out.println("");
        return buff;
    }

    public static ArrayList<Double> play(ArrayList<Double> buff, Double speed) {
        music_play(buff, speed);
        return buff;
    }

    public static ArrayList<Double> loop (ArrayList<Double> buff, double times) {
        ArrayList<Double> localbuff = new ArrayList<Double>();
        int fulltimes=(int)times;
        for(int i = 0; i< fulltimes; i++)
            localbuff.addAll(buff);
        int partial = (int) ((times - fulltimes)*(double)buff.size());
        for(int i = 0; i< partial; i++)
            localbuff.add(buff.get(i));

        return localbuff;
    }

    public static ArrayList<Double> fill(ArrayList<Double> buff, Double size) {
        ArrayList<Double> localBuff = new ArrayList<Double>();

        Double total = size * beat;
        Double zeros = total > buff.size() ? total - buff.size() : 0 ;

        for(int i = 0; i < buff.size(); i++) localBuff.add(buff.get(i));

        for(int i = 0; i < zeros; i++) localBuff.add(0.0);

        return buff;
    }

    public static ArrayList<Double> tune(ArrayList<Double> buff, Double pitch) {
        return resample(buff, (int)(buff.size()*(Math.pow(Math.pow(2, 1/12.0),-pitch))));
    }

}


