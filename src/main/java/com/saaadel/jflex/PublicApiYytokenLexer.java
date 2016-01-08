package com.saaadel.jflex;

/**
 * Interface for JFlex default lexer (without %byaccj, %cup and etc.)
 *
 * @see %byaccj
 * @see %cup
 */
public interface PublicApiYytokenLexer extends PublicApiLexer {
    /**
     * Resumes scanning until the next regular expression is matched,
     * the end of input is encountered or an I/O-Error occurs.
     *
     * @return the next token
     * @throws java.io.IOException if any I/O-Error occurs
     */
    public Yytoken yylex() throws java.io.IOException;
}
