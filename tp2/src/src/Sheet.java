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

    public void shiftTone(int offset){
        for(int j=0; j<sheet.size(); j++){
            Note n = sheet.get(j);
            if(offset>0) {
                for(int i =0; i<offset; i++)
                {
                    if(n.tone==Tone.GS)
                        n.octave=n.octave+1;
                    n.tone=getNextLower(n.tone);
                }
            }
            if(offset<0){
                for(int i =0; i<offset; i++)
                {
                    if(n.tone==Tone.A)
                        n.octave=n.octave-1;
                    assert (n.octave>=0);
                    n.tone=getNextHigher(n.tone);
                }

            }
        }
    }
    private static Tone getNextLower(Tone t){
        switch (t) {
            case A:
                return Tone.GS;
            case AS:
                return Tone.A;
            case B:
                return Tone.AS;
            case C:
                return Tone.B;
            case CS:
                return Tone.C;
            case D:
                return Tone.CS;
            case DS:
                return Tone.D;
            case E:
                return Tone.DS;
            case F:
                return Tone.E;
            case FS:
                return Tone.F;
            case G:
                return Tone.FS;
            case GS:
                return Tone.G;
            default:
                return Tone.A;

        }
    }
    private static Tone getNextHigher(Tone t){
        switch (t) {
            case A:
                return Tone.AS;
            case AS:
                return Tone.B;
            case B:
                return Tone.C;
            case C:
                return Tone.CS;
            case CS:
                return Tone.D;
            case D:
                return Tone.DS;
            case DS:
                return Tone.E;
            case E:
                return Tone.F;
            case F:
                return Tone.FS;
            case FS:
                return Tone.G;
            case G:
                return Tone.GS;
            case GS:
                return Tone.A;
            default:
                return Tone.A;

        }
    }

    private ArrayList<Note> sheet;

}
