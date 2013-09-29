grammar gram;
prog:   (S NEWLINE)*;
NEWLINE : [\r\n]+;
PLUS    : '+';
MINUS   : '-';
SIGN    : (PLUS|MINUS);

S
    : L
    | L'.'M
    ;

L
    : E | E ';' | E ';' L
    | '{' L '}'
    | '{' L '}' OP '{' L '}'
    | '{' L '}' OP E
    ;

fragment OP
    : '&' | ';' | '+' | '-' | '*' | '/'
    | 'mix' | 'con' | 'add' | 'sub' | 'mul' | 'div'
    ;


num 
    : SIGN? DIGS ('.' DIGS | DIGS )
    ;

funcion
    : 'sin(' num ',' num ')' 
    | 'lin(' num ',' num ')' 
    | 'linear(' num ',' num ')' 
    | 'sil()' 
    | 'silence()' 
    | 'noi(' num ')'  
    | 'noise(' num ')'
    ;
E
    : funcion
    | num
    ;


fragment DIGS
    : '1'..'9' '0'..'9'*
    ;

M
    : 'expand' | 'reduce' | 'post' | '.'Mpunto | '.'Mpunto M
    ;

Mpunto 
    : 'loop(' DIGS')' | 'fill('DIGS ')' |'tune(' SIGN DIGS ')' | 'play(' DIGS'  )'
    ;

