public class Note {
    public Note() {
        tone = Tone.A;
        octave = 0;
        duration = Duration.Quarter;
    }

    public Note(Tone _tone, int _octave, Duration _duration) {
        tone = _tone;
        octave = _octave;
        duration = _duration;
    }

    @Override
    public String toString() {
        return "Octave: " + Integer.toString(octave);
    }

    public Tone tone;
    public int octave;
    public Duration duration;
}
