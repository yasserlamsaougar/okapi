grammar Filter;


expr
 : BIND_PARAMETER operator literal_value
 | literal_value operator BIND_PARAMETER
 | BIND_PARAMETER operator literal_value
 | literal_value operator BIND_PARAMETER
 | expr K_AND expr
 | expr K_OR expr
 | OPEN_PAR expr CLOSE_PAR

;

operator
 :( LT | LT_EQ | GT | GT_EQ )
 |( EQ | NOT_EQ1 | NOT_EQ2 )
;

literal_value
 : STRING_LITERAL
 | REGEX_LITERAL
;


OPEN_PAR : '(';
CLOSE_PAR : ')';
LT : '<';
LT_EQ : '<=';
GT : '>';
GT_EQ : '>=';
EQ : '==';
NOT_EQ1 : '!=';
NOT_EQ2 : '<>';

K_AND: A N D;
K_OR : O R;

IDENTIFIER
 : '"' (~'"' | '""')* '"'
 | '`' (~'`' | '``')* '`'
 | '[' ~']'* ']'
 | ([a-zA-Z_] [a-zA-Z_0-9]*) // TODO check: needs more chars in set
 ;

BIND_PARAMETER
 : ('@'|('%'([a-zA-Z_\-] [a-zA-Z_0-9\-]*))':') IDENTIFIER
 ;

STRING_LITERAL
 : '\'' ( ~'\'' | '\'\'' )* '\''
 ;
REGEX_LITERAL
 : '%%'[a-zA-Z0-9{}.$^!/\\_\-*+:;,[\]|]+
 ;


SPACES
 : [ \u000B\t\r\n] -> channel(HIDDEN)
 ;

UNEXPECTED_CHAR
 : .
 ;

fragment DIGIT : [0-9];

fragment A : [aA];
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];