import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;


public class Main {
    public enum Note {
        A, AS, B, C, CS, D, DS, E, F, FS, G, GS
    }

    private static void print(String a) {
        System.out.println(a);
    }

    public static void main(String[] args) {

        print("PreSonido");
        int duration = 500;
        makeSound(getTone(getNote(Note.G, 0)), duration);
        makeSound(getTone(getNote(Note.G, 0)), duration);
        makeSound(getTone(getNote(Note.G, 0)), duration);
        makeSound(getTone(getNote(Note.A, 1)), duration);
        makeSound(getTone(getNote(Note.B, 1)), duration);
        makeSound(getTone(getNote(Note.G, 0)), duration);

        print("PostSonido");
    }

    private static int getNote(Note note, int octave) {
        int offset_note;
        switch (note) {
            case A:
                offset_note = 1;
                break;
            case AS:
                offset_note = 2;
                break;
            case B:
                offset_note = 3;
                break;
            case C:
                offset_note = 4;
                break;
            case CS:
                offset_note = 5;
                break;
            case D:
                offset_note = 6;
                break;
            case DS:
                offset_note = 7;
                break;
            case E:
                offset_note = 8;
                break;
            case F:
                offset_note = 9;
                break;
            case FS:
                offset_note = 10;
                break;
            case G:
                offset_note = 11;
                break;
            case GS:
                offset_note = 12;
                break;
            default:
                offset_note = 0;
                break;
        }
        print("offsetnote: " + Integer.toString(offset_note) + "  octave: " + Integer.toString(octave));
        return offset_note + (octave * 12) - 1;
    }

    private static int getTone(int offset) {
        print("getNote: " + Integer.toString(offset));
        double twelfth_root_of_two = 1.0594630943592953f;
        Double temper = 440.0f * Math.pow(twelfth_root_of_two, offset);


        return temper.intValue();
    }

    private static void makeSound(int note, int duration) {
        print("note: " + Integer.toString(note));
        byte[] buf = new byte[1];
        int sampling_freq = 44100;
        try {
            AudioFormat af = new AudioFormat((float) sampling_freq, 8, 1, true, false);
            SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
            sdl = AudioSystem.getSourceDataLine(af);
            sdl.open(af);
            sdl.start();
            for (int i = 0; i < duration * (float) sampling_freq / duration; i++) {
                double angle = i / ((float) sampling_freq / note) * 2.0 * Math.PI;
                buf[0] = (byte) (Math.sin(angle) * 100);
                sdl.write(buf, 0, 1);
            }
            sdl.drain();
            sdl.stop();
            sdl.close();
        } catch (Exception ex) {
            print("fin del mundo " + ex.getMessage());
        }

    }
}