import java.util.ArrayList;


public class Main {


    private static void print(String a) {
        System.out.println(a);
    }

    public static void main(String[] args) {

        print("PreSonido");
        Note g0 = new Note(Tone.G, 0, Duration.Quarter);
        //makeSound(getTone(getNote(Note.G, 0)), duration);
        Note a1 = new Note(Tone.A, 1, Duration.Quarter);
        //makeSound(getTone(getNote(Note.A, 1)), duration);
        Note b1 = new Note(Tone.B, 1, Duration.Half);
        //makeSound(getTone(getNote(Note.B, 1)), duration);
        //makeSound(getTone(getNote(Note.G, 0)), duration);
        ArrayList<Note> sheet = new ArrayList<Note>();
        sheet.add(g0);
        sheet.add(g0);
        sheet.add(g0);
        sheet.add(a1);
        sheet.add(b1);
        sheet.add(g0);
        sheet.add(b1);
        sheet.add(b1);
        sheet.add(a1);
        sheet.add(a1);
        sheet.add(g0);
        Sheet s = new Sheet(sheet);
        s.play();
        print("PostSonido");
    }


}