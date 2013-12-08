import java.util.ArrayList;
import org.antlr.runtime.*;

public class Main {
	

    private static void print(Object a) {
        System.out.println(a);
    }
    public static void main(String[] args) throws Exception {   	
        ANTLRStringStream in = new ANTLRStringStream("{{1;20}+{5;5;3*4;2}}.loop(10).post.play");
        colliderLexer lexer = new colliderLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        colliderParser parser = new colliderParser(tokens);
    	ArrayList<Double> toPlay = parser.sGram().value;
        print(toPlay.size()); // print the value
        //Buffer.music_play(toPlay,1.0);
    }

}
