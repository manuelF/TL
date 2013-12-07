import java.util.ArrayList;
import org.antlr.runtime.*;

public class Main {
	

    private static void print(Object a) {
        System.out.println(a);
    }
    public static void main(String[] args) throws Exception {   	
        ANTLRStringStream in = new ANTLRStringStream("sin(1)");
        colliderLexer lexer = new colliderLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        colliderParser parser = new colliderParser(tokens);
    	ArrayList<Double> toPlay = parser.generador().value;
        print(toPlay.size()); // print the value
        Buffer.music_play(toPlay);
    }

}
