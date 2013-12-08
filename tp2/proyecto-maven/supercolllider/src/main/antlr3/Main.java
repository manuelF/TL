import java.util.ArrayList;
import org.antlr.runtime.*;

public class Main {
	

    private static void print(Object a) {
        System.out.println(a);
    }
    public static void main(String[] args) throws Exception {   	
        
        String input;
        input="{lin(-100,30) * sin(440) ; sil ; noi(2) ; sil}.post.play(0.1)";
        //input="{ 1; -1 }.loop(3).play"; //onda cuadrada
		//input="{{ sin(8); sin(4); sin(2); sin(1) } * lin( 1.0, 0.0) }.expand(12).play"; //drums
		//input="{ { sin( 16, 0.9)+noise(0.1) }*lin( 1.0, 0.1) }.expand(12).play "; //Snare
		//input="{ noise(1) ; sin(1); noise(1) ; noise(1).loop(3) * lin(1.0, 0.25); noise(1).loop(6) * lin(0.25, 0.0)}.play"; //claps
		//input="{ { sin(8); sin(4); sin(2); sin(1) }.reduce(1) * lin(1,0) ; silence.loop(7) } mix { { sin( 16, 0.9) + noise(0.1) } * lin( 1.0, 0.1); silence.loop(3) }.loop(2) } mix { { { noi*lin( 1.0, 0.25); noi.loop(7)*lin( 0.25, 0.0) }.reduce; sil }.loop(4)  } }.loop(16).play"; //varios instrumentos
        if(args.length!=0){
            input = args[0];
        }
        ANTLRStringStream in = new ANTLRStringStream(input);
        colliderLexer lexer = new colliderLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        colliderParser parser = new colliderParser(tokens);
    	ArrayList<Double> toPlay = parser.sGram().value;
        print(toPlay.size()); // print the value
        //Buffer.music_play(toPlay,1.0);
    }

}
