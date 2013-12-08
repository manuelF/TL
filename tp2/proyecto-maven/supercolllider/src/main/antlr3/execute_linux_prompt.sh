#! /bin/bash
echo "===Compilando la gramatica==="
java -jar antlr-3.3-complete.jar collider.g;
echo "===Compilando el Prompt==="
javac -cp .:antlr-3.3-complete.jar Prompt.java;
echo "===Corriendo el prompt==="
java -cp .:antlr-3.3-complete.jar Prompt;
echo "===Borrando los .class==="
rm -f *.class;
