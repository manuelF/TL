public class Note {
    public Note() {
        tone = Tone.A;
        octave = 0;
        duration = Duration.Quarter;
    }

    public Note(Note other) {
        this.tone = other.tone;
        this.octave = other.octave;
        this.duration = other.duration;
    }

    public Note(Tone _tone, int _octave, Duration _duration) {
        tone = _tone;
        octave = _octave;
        duration = _duration;
    }

    @Override
    public String toString() {
        return "Tone: " + tone.name() +
                " Octave: " + Integer.toString(octave) +
                " Duration: " + duration.name();
    }

    public Tone tone;
    public int octave;
    public Duration duration;
}
