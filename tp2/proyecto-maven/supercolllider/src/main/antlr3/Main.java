import java.util.ArrayList;
import org.antlr.runtime.*;

public class Main {
	

    private static void print(Object a) {
        System.out.println(a);
    }
    public static void main(String[] args) throws Exception {   	
        
        String input;
        //Cancion 1
        input="{{{sil.loop(3);sin(10.9).loop(3);sin(13.73).loop(3);sin(14.55).loop(3);sin(16.33).loop(12)}.loop(2)};{sil.loop(3);sin(10.9).loop(3);sin(13.73).loop(3); sin(14.55).loop(3); sin(16.33).loop(6); sin(13.73).loop(6); sin(10.9).loop(6); sin(13.73).loop(6);sin(12.23).loop(18)};{sin(13.73).loop(3);sin(12.23).loop(3);sin(10.9).loop(6);sin(10.9).loop(3);sin(13.73).loop(6); sin(16.33).loop(6); sin(16.33).loop(3);sin(14.55).loop(6)};{sil.loop(6); sin(13.73).loop(3);sin(14.55).loop(3); sin(16.33).loop(6);sin(13.73).loop(6);sin(12.23).loop(6);sin(12.23).loop(6);sin(10.9).loop(15);sil.loop(9)}}.play";
        //Cancion 2
//        input="{{sin(12.23).loop(1.5);sin(12.23).loop(1.5);sin(12.23).loop(1.5);sin(16.33).loop(6);sin(24.47).loop(6);{sin(21.80).loop(1.5);sin(20.57).loop(1.5);sin(18.33).loop(1.5);sin(32.66).loop(6);sin(24.47).loop(3)}.loop(2)};{sin(21.80).loop(1.5);sin(20.57).loop(1.5);sin(21.80).loop(1.5);sin(18.33).loop(6);sin(12.23).loop(3);sin(12.23).loop(1.5);sin(16.33).loop(6);sin(24.47).loop(6);{sin(21.80).loop(1.5);sin(20.57).loop(1.5);sin(18.33).loop(1.5);sin(32.66).loop(6);sin(24.47).loop(3)}.loop(2)};{sin(21.80).loop(1.5);sin(20.57).loop(1.5);sin(21.80).loop(1.5);sin(18.33).loop(6)}}.play";
        //input="{lin(-100,30) * sin(440) ; sil ; noi(2) ; sil}.post.play(0.1)";
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
    }

}
