package com.saaadel.jflex;

/**
 * Interface for JFlex lexer (BYacc/J compatibility)
 *
 * @see %byaccj
 */
public interface PublicApiByaccjLexer extends PublicApiLexer {
    /**
     * Resumes scanning until the next regular expression is matched,
     * the end of input is encountered or an I/O-Error occurs.
     *
     * @return the next token
     * @throws java.io.IOException if any I/O-Error occurs
     */
    int yylex() throws java.io.IOException;
}
