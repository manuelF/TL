grammar collider;

options {
    // antlr will generate java lexer and parser
    language = Java;
    // generated parser should create abstract syntax tree
    output = AST;
}

@parser::members {
  public static double getDouble(String text){
    return Double.parseDouble(text);
  }
}

/* Reglas de Lexer */
/* Nos comemos el whitespace */
WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+    { $channel = HIDDEN; } ;
/* Homogenizamos los nombres de las funciones */
SIN: 	'sin' | 'SIN' | 'Sin'
	;

LIN:	'lin' | 'LIN' | 'Lin'
	|'linear'| 'LINEAR' | 'Linear'
	;

SIL:	'sil' | 'SIL' | 'Sil'
	|'silence' | 'SILENCE' | 'Silence'
	;

NOI:	'noi' | 'NOI' | 'Noi'
	|'noise' | 'NOISE' | 'Noise'
	;
/* op:	mix | con | add | sub | mul | div*/
/* Tokenizamos los simbolos de operaciones */
MIX:	'mix' | '&'
	;
CON:	'con' | ';'
	;
ADD:	'add' | '+'
	;
SUB:	'sub' | '-'
	;
MUL:	'mul' | '*'
	;
DIV:	'div' | '/'
	;
/* Definimos los numeros con la regex que acepta punto flotante sin notacion cientifica */
NUM	:('+' | '-' | ) ('1'..'9'('0'..'9')* | '0' )('.'('0'..'9')*('1'..'9') | '.0' | )
	;
PERIOD:	'.'
	;

PR_START: '('
	;
PR_END:	')'
	;

BR_START: '{'
  ;
BR_END: '}'
  ;
/* Reglas de Parser */ 

/* La gramatica es un buffer con varios metodos aplicados. hubo que factorizar */ 

sGram returns [ArrayList<Double> value]: 
  b=buffer EOF {$value = $b.value;}
  ;

	
buffer returns [ArrayList<Double> value]:	
  mixExpr (CON mixExpr)* {$value = new ArrayList<Double>();}
  ;

mixExpr returns [ArrayList<Double> value]:
  sumaRestaExpr (MIX sumaRestaExpr)* {$value = new ArrayList<Double>();}
  ;

sumaRestaExpr returns [ArrayList<Double> value]:
  mulDivExpr (sumaResta mulDivExpr)* {$value = new ArrayList<Double>();} 
  ;
  
mulDivExpr returns [ArrayList<Double> value]:
  bufferAtom (mulDiv bufferAtom)* {$value = new ArrayList<Double>();}
  ;
  
bufferAtom returns [ArrayList<Double> value]:
  gen=generador sec1=sec_metodos[$gen.value] {$value = $sec1.value;}
  |
  BR_START b=buffer BR_END sec2=sec_metodos[$b.value] {$value = $sec2.value;}  
  ;
  
sumaResta:
  ADD
  |
  SUB
  ;
        
mulDiv:
  MUL
  |
  DIV
  ;
  



generador returns [ArrayList<Double> value]:
  s=sin {$value = $s.value;}
  |
	lin {$value = $s.value;}
	| 
	sil {$value = Buffer.sil();} 
	| 
	noi {$value = $s.value;}
	|
	a=NUM {$value = Buffer.buffer(getDouble($a.text));}
	;
	
sin returns [ArrayList<Double> value]: 
  SIN PR_START c=NUM (PR_END | ',' a=NUM PR_END) {if($a.text == null){ 
                                                    $value = Buffer.sin(getDouble($c.text),1);
                                                  } else {
                                                    $value = Buffer.sin(getDouble($c.text),getDouble($a.text));
                                                  }
                                                 }
  ;
  
  
lin returns [ArrayList<Double> value]:
  LIN PR_START a=NUM ',' b=NUM PR_END {$value = Buffer.lin(getDouble($a.text), getDouble($a.text));}
  ;
  
sil: 
  SIL
  ;
  
noi returns [ArrayList<Double> value]:
  NOI PR_START a=NUM PR_END {$value = Buffer.noi(getDouble($a.text));}
  ;
   
  
/* Instanciamos un metodo que puede recibir parametros */	
sec_metodos[ArrayList<Double> buffInicial] returns [ArrayList<Double> value]:	
	'.' m=metodo[buffInicial] s=sec_metodos[$metodo.value] {$value = $s.value;}
	| {$value = new ArrayList<Double>();}
	;

/* Los metodos que reciben parametros, algunos obligatorios */
metodo[ArrayList<Double> buff] returns [ArrayList<Double> value]:	
  'expand' param{$value = new ArrayList<Double>();}
  | 
  'reduce' param{$value = new ArrayList<Double>();}
  | 
  'post' {$value = new ArrayList<Double>();}
	| 
	'play' (p=param | ) {$value = new ArrayList<Double>();}
	| 
	'loop' a=param {$value = new ArrayList<Double>();}/* {$value = Buffer.loop(getDouble($a.text)} */
	| 
	'fill' param {$value = new ArrayList<Double>();}
	| 
	'tune' param{$value = new ArrayList<Double>();}
	;

/* Los parametros pueden ser numeros, o sino, sin parentesis*/
param returns [double value]:	PR_START n=NUM PR_END {$value = getDouble($n.text);}
	; 

