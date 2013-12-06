import java.util.ArrayList;


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
}


