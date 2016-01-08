package com.saaadel.m3u2opml.lexer;
import com.saaadel.jflex.*;
import com.saaadel.m3u2opml.lexer.Token;

//import java_cup.runtime.*;
//import jflex.sym;

%%

%char
%column
%line

%unicode

%apiprivate
//%implements com.saaadel.jflex.PublicApiLexer

//%implements com.saaadel.jflex.PublicApiYytokenLexer

//%cup
//%implements com.saaadel.jflex.CupLexer

//%byaccj
//%implements com.saaadel.jflex.PublicApiByaccjLexer

%public
%class M3U8Lexer
%implements com.saaadel.jflex.GenericLexer<com.saaadel.m3u2opml.lexer.Token>
%type com.saaadel.m3u2opml.lexer.Token

%{
    /**
     * Resumes scanning until the next regular expression is matched,
     * the end of input is encountered or an I/O-Error occurs.
     *
     * @return      the next token
     * @exception   java.io.IOException  if any I/O-Error occurs
     */
    public com.saaadel.m3u2opml.lexer.Token nextToken() throws java.io.IOException {
        return yylex();
    }

    private final StringBuffer string = new StringBuffer();

    private Token getToken(Token.Type type) {
        return new Token(type, yyline, yycolumn, yychar);
    }

    private Token getToken(Token.Type type, Object value) {
        return new Token(type, yyline, yycolumn, yychar, value);
    }
%}

// ==============================================================================================================

LineTerminator = \r\n|\r|\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

DecIntegerLiteral = 0 | [1-9][0-9]*

EXTINF = "#EXTINF:" \d+,
EXTINFName = {InputCharacter}+

PATH = [^#\r\n] {InputCharacter}+

%xstate YYEXTINF

%%

<YYINITIAL> "#EXTM3U"           { return getToken(Token.Type.EXTM3U); }
<YYINITIAL> {
    {EXTINF}                     { string.setLength(0); yybegin(YYEXTINF); //return getToken(Token.Type.EXTINF, yytext());
                                }

    {WhiteSpace}                { /* ignore */ //return getToken(Token.Type.IGNORING, yytext());
                                }

    {PATH}  / {LineTerminator}                     { return getToken(Token.Type.PATH, yytext()); }
}

<YYEXTINF> {
    {EXTINFName} / {LineTerminator}     { yybegin(YYINITIAL); return getToken(Token.Type.EXTINF, yytext()); }

    {LineTerminator}     { yybegin(YYINITIAL); return getToken(Token.Type.IGNORING, yytext()); }
}

/* error fallback */
[^]                              { throw new IllegalStateException("Illegal character <"+
                                                    yytext()+">"); }