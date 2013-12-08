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
  private static void print(Object a) {
        System.out.println(a);
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
  b1=mixExpr {$value = $b1.value;} (CON b2=mixExpr {$value = Utils.Concatenate($value,$b2.value);})* 
  ;

mixExpr returns [ArrayList<Double> value]:
  b1=sumaRestaExpr {$value = $b1.value;} (MIX b2=sumaRestaExpr {$value=Buffer.oper("m",$value,$b2.value);})* 
  ;

sumaRestaExpr returns [ArrayList<Double> value]:
  b1=mulDivExpr {$value = $b1.value;} (op=sumaResta b2=mulDivExpr {if ($op.value) $value=Buffer.oper("+",$value,$b2.value); else $value=Buffer.oper("-",$value,$b2.value);})* 
  ;
  
mulDivExpr returns [ArrayList<Double> value]:
  b1=bufferAtom {$value = $b1.value;} (op=mulDiv b2=bufferAtom {if ($op.value) $value=Buffer.oper("*",$value,$b2.value); else $value=Buffer.oper("/",$value,$b2.value);})*
  ;
  
bufferAtom returns [ArrayList<Double> value]:
  gen=generador sec1=sec_metodos[$gen.value] {$value = $sec1.value;}
  |
  BR_START b=buffer BR_END sec2=sec_metodos[$b.value] {$value = $sec2.value;}  
  ;
  
sumaResta returns [boolean value]:
  ADD {$value = true;} 
  |
  SUB {$value = false;}
  ;
        
mulDiv returns [boolean value]:
  MUL {$value = true;} 
  |
  DIV {$value = false;} 
  ;
  



generador returns [ArrayList<Double> value]:
  sin {$value = $sin.value;}
  |
	lin {$value = $lin.value;}
	| 
	sil {$value = Buffer.sil();} 
	| 
	noi {$value = $noi.value;}
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
  
sil returns [ArrayList<Double> value]: 
  SIL {$value = Buffer.sil();}
  ;
  
noi returns [ArrayList<Double> value]:
  NOI PR_START a=NUM PR_END {$value = Buffer.noi(getDouble($a.text));}
  ;
   
  
/* Instanciamos un metodo que puede recibir parametros */	
sec_metodos[ArrayList<Double> buffInicial] returns [ArrayList<Double> value]:	
	'.' m=metodo[buffInicial] s=sec_metodos[m.value] {$value = $s.value;}
	| {$value = $buffInicial;}
	;

/* Los metodos que reciben parametros, algunos obligatorios */
metodo[ArrayList<Double> buff] returns [ArrayList<Double> value]:	
  'expand' param{$value = Buffer.expand($buff, $param.value);}
  | 
  'reduce' param{$value = Buffer.reduce($buff, $param.value);}
  | 
  'post' {$value = Buffer.post($buff);}
	| 
	'play' (p=param {$value = Buffer.play($buff,$p.value);}| {$value = Buffer.play($buff,1.0);})
	| 
	'loop' a=param {$value = Buffer.loop($buff, $a.value);}
	| 
	'fill' a=param {$value = Buffer.fill($buff, $a.value);}
	| 
	'tune' a=param {$value = Buffer.tune($buff, $a.value);}
	;

/* Los parametros pueden ser numeros, o sino, sin parentesis*/
param returns [Double value]:	PR_START n=NUM PR_END {$value = getDouble($n.text);}
	; 

