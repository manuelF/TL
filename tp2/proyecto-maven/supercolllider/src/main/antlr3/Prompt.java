import java.util.ArrayList;
import org.antlr.runtime.*;

import java.util.Scanner;

public class Prompt {
	

    private static void print(Object a) {
        System.out.println(a);
    }
    public static void main(String[] args) throws Exception {   	
        Scanner stringInput = new Scanner(System.in);
        String input;
        System.out.print("> ");
        while(stringInput.hasNextLine())
        {
            input = stringInput.nextLine();

            ANTLRStringStream in = new ANTLRStringStream(input);
            colliderLexer lexer = new colliderLexer(in);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            colliderParser parser = new colliderParser(tokens);
            ArrayList<Double> toPlay = parser.sGram().value;
            print(toPlay.size()); // print the value
            System.out.print("> ");
        }
    }

}
