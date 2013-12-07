#! /bin/bash
echo "===Compilando la gramatica==="
java -jar antlr-3.3-complete.jar collider.g;
echo "===Compilando el main==="
javac -cp .:antlr-3.3-complete.jar Main.java;
echo "===Corriendo el main==="
java -cp .:antlr-3.3-complete.jar Main;
echo "===Borrando los .class==="
rm -f *.class;
