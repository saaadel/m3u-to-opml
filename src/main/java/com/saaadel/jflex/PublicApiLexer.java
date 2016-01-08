package com.saaadel.jflex;

/**
 * Interface for JFlex lexer
 */
public interface PublicApiLexer {
    /**
     * This character denotes the end of file
     */
    int YYEOF = -1;
    /**
     * default lexical state
     */
    int YYINITIAL = 0;

    void yyclose() throws java.io.IOException;

    void yyreset(java.io.Reader reader);

    int yystate();

    void yybegin(int newState);

    String yytext();

    char yycharat(int pos);

    int yylength();

    void yypushback(int number);
}
