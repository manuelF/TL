grammar gram;
prog:   (s)*;
PLUS    : '+';
MINUS   : '-';
sign    : (PLUS|MINUS);

s
   :l'.'m
   ;

l
    : e | e ';' | e ';' l
    | '{' l '}' findelista
    ;
findelista
	: OP '{' l '}'
	 |OP e
	|
	;

fragment OP
    : '&' | ';' | '+' | '-' | '*' | '/'
    | 'mix' | 'con' | 'add' | 'sub' | 'mul' | 'div'
    ;


NUM 
    : sign? DIGS '.'? DIGS)
    ;

FUNCION
    : 'sin(' NUM ',' NUM ')' 
    | 'lin(' NUM ',' NUM ')' 
    | 'linear(' NUM ',' NUM ')' 
    | 'sil()' 
    | 'silence()' 
    | 'noi(' NUM ')'  
    | 'noise(' NUM ')'
    ;
e
    : FUNCION
    | NUM
    ;


fragment DIGS
    : '1'..'9' '0'..'9'*
    ;

m
    : 'expand' | 'reduce' | 'post' | '.'mpunto | '.'mpunto m
    ;

mpunto 
    : 'loop(' DIGS')' | 'fill('DIGS ')' |'tune(' sign DIGS ')' | 'play(' DIGS'  )'
    ;

