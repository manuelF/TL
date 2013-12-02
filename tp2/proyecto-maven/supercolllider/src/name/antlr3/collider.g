@lexer::header {
  package org.meri.antlr_step_by_step.parsers;
}
 
@parser::header {
  package org.meri.antlr_step_by_step.parsers;
}

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
/* Reglas de Parser */ 

/* La gramatica es una sucesion de metodos, o un buffer con varios metodos aplicados */ 

gram:	sec_metodos | buffer sec_metodos /* r */
	;
/* Falta definir r que tiene un conflicto por un 'b' que no existe*/
	
buffer:	NUM | funcion 
	;

funcion: 
	generadora repeticiones
	;

generadora:	
	SIN | LIN | SIL | NOI
	;
/* Si no hay repeticiones, lambda, es por default 1 (creo) */
repeticiones:	
	PR_START NUM sec_repeticiones
	|
	;
/* la cola de repeticiones */
sec_repeticiones:
	','NUM | PR_END
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

op:	MIX | CON | ADD | SUB | MUL | DIV 
	;
