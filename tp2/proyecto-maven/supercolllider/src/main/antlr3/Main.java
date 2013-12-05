import java.util.ArrayList;

import org.antlr.runtime.*;

public class Main {


    private static void print(String a) {
        System.out.println(a);
    }
    public static void main(String[] args) throws Exception {   	
        ANTLRStringStream in = new ANTLRStringStream("1.4");
        colliderLexer lexer = new colliderLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        colliderParser parser = new colliderParser(tokens);
        System.out.println(parser.generador()); // print the value
    	 
    }

}
