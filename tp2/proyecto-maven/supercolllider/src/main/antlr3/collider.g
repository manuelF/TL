grammar collider;

options {
    // antlr will generate java lexer and parser
    language = Java;
    // generated parser should create abstract syntax tree
    output = AST;
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

sGram: buffer EOF
  ;

	
buffer:	
  NUM bufferPrima 
  | 
  generadora bufferPrima
  |
  BR_START buffer BR_END bufferPrima    
	;

bufferPrima:
  op buffer bufferPrima
  |
  ';' buffer bufferPrima
  |
  sec_metodos
  |
  ;
	


generadora:	
	sin 
	| 
	lin 
	| 
	sil 
	| 
	noi
	;
	
sin: 
  SIN PR_START NUM sinPrima
  ;
  
sinPrima:
  PR_END
  |
  ',' NUM PR_END
  ;
  
lin: 
  LIN PR_START NUM ',' NUM PR_END
  ;
  
sil: 
  SIL
  ;
  
noi: 
  NOI PR_START NUM PR_END
  ;
  
op: MIX | CON | ADD | SUB | MUL | DIV 
        ;  
  
/* Instanciamos un metodo que puede recibir parametros */	
sec_metodos:	
	'.' metodo sec_metodos
	|
	;

/* Los metodos que reciben parametros, algunos obligatorios */
metodo:	'expand' | 'reduce' | 'post' 
	| 'play' param 
	| 'loop' PR_START NUM PR_END 
	| 'fill' PR_START NUM PR_END 
	| 'tune' PR_START NUM PR_END
	;

/* Los parametros pueden ser numeros, o sino, sin parentesis*/
param:	PR_START NUM PR_END
	|
	; 

