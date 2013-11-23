import java.util.ArrayList;

public class Sheet {
    public Sheet() {
        sheet = new ArrayList<Note>();
    }

    public Sheet(ArrayList<Note> other_sheet) {
        sheet = other_sheet;
    }

    public void play() {
        MusicBox.play(this.sheet);
    }

    private ArrayList<Note> sheet;

}
