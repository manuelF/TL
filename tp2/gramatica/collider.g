grammar collider;

METODO: 'post'
	;

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

BR_START: '{'
	;
BR_END: '}'
	;
COLON:	';'
	;
NUM	:('+' | '-' | ) ('1'..'9'('0'..'9')* | '0' )('.'('0'..'9')*('1'..'9') | '.0' | )
	;
PERIOD:	'.'
	;
OP:	'mix' | 'con' | 'add' | 'sub' | 'mul' | 'div'
	;
PR_START: '('
	;
PR_END:	')'
	;


s:	n | g n
	;
/*Falta definir r*/
	
g:	NUM | funcion
	;
funcion: f h
	;
f:	'sin' | 'lin' | 'sil' | 'noi'
	;
h:	PR_START NUM i
	|
	;

i:	','NUM | PR_END
	;
	
n:	'.' m n
	|
	;
m:	'expand' | 'reduce' | 'post' | 'play' p | 'loop' PR_START NUM PR_END | 'fill' PR_START NUM PR_END | 'tune' PR_START NUM PR_END
	;
p:	PR_START NUM PR_END
	|
	; 

