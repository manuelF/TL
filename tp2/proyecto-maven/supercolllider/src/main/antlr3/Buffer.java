import java.util.ArrayList;


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

    //TODO
    public static ArrayList<Double> lin (double a, double b) {
        ArrayList<Double> buff = new ArrayList<Double>();
        return buff;
    }


}


