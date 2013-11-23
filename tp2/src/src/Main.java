import java.util.ArrayList;


public class Main {


    private static void print(String a) {
        System.out.println(a);
    }

    public static void main(String[] args) {

        print("PreSonido");
        Note g0 = new Note(Tone.G, 0, Duration.Quarter);
        Note g0l = new Note(Tone.G, 0, Duration.Whole);
        //makeSound(getTone(getNote(Note.G, 0)), duration);
        Note a1 = new Note(Tone.A, 1, Duration.Quarter);
        //makeSound(getTone(getNote(Note.A, 1)), duration);
        Note b1 = new Note(Tone.B, 1, Duration.Half);
        Note b1s = new Note(Tone.B, 1, Duration.Quarter);
        //makeSound(getTone(getNote(Note.B, 1)), duration);
        //makeSound(getTone(getNote(Note.G, 0)), duration);
        ArrayList<Note> sheet = new ArrayList<Note>();
        sheet.add(new Note(g0));
        sheet.add(new Note(g0));
        sheet.add(new Note(g0));
        sheet.add(new Note(a1));
        sheet.add(new Note(b1));
        sheet.add(new Note(g0));
        sheet.add(new Note(b1s));
        sheet.add(new Note(b1s));
        sheet.add(new Note(a1));
        sheet.add(new Note(a1));
        sheet.add(new Note(g0l));
        Sheet s = new Sheet(sheet);
        s.shiftTone(-2);
        s.play();
        print("PostSonido");
    }


}