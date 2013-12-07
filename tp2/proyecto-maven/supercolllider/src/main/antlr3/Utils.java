
import java.util.ArrayList;
public class Utils{
public static ArrayList<Double> Concatenate(
            ArrayList<Double> l,
            ArrayList<Double> r ){

        ArrayList<Double> ret = new ArrayList<Double>();
        ret.addAll(l);
        ret.addAll(r);
        ret.trimToSize();
        return ret;
    
    }

}
