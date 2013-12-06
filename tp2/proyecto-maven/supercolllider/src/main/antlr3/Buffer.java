import java.util.ArrayList;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;


public class Buffer {
    public static double sampling_rate = 44100;
    public static double beat = sampling_rate / 12;

	public static ArrayList<Double> buffer(double number) {
	    ArrayList<Double> list = new ArrayList<Double>();
        list.add(number);
        return list;
	}

    public static ArrayList<Double> sin (double c, double a) {
        ArrayList<Double> buff = new ArrayList<Double>();
        double x = (c * 2 * Math.PI)/beat;

        for (int i = 0; i < beat; i++){
            buff.add(a * Math.sin(i * x));
        }
        return buff;
    }

    public static ArrayList<Double> play(ArrayList<Double> buff){
        for(Double note: buff){
            note_play(note.intValue(),beat);
        }

        return buff;
    }
//    private static void note_play(Double pitch){
    private static void note_play(int note, double duration) {
        byte[] buf = new byte[1];
        double sampling_freq =sampling_rate;

            try {
            AudioFormat af = new AudioFormat((float) sampling_freq, 8, 1, true, false);
            SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
            sdl = AudioSystem.getSourceDataLine(af);
            sdl.open(af);
            sdl.start();
            for (int i = 0; i < duration; i++) {
                double angle = i / (sampling_freq / note) * 2.0 * Math.PI;
                buf[0] = (byte) (Math.sin(angle) * 100);
                sdl.write(buf, 0, 1);
            }
            sdl.drain();
            sdl.stop();
            sdl.close();
        } catch (Exception ex) {
            System.out.println("fin del mundo " + ex.getMessage());
        }
    }
}


