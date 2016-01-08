package com.saaadel.jflex;

/**
 * Basic interface for JFlex generic lexers with private api.
 *
 * @see %apiprivate
 */
public interface GenericLexer<TOKEN extends Yytoken> {
    /**
     * Resumes scanning until the next regular expression is matched,
     * the end of input is encountered or an I/O-Error occurs.
     *
     * @return the next token
     * @throws java.io.IOException if any I/O-Error occurs
     */
    public TOKEN nextToken() throws java.io.IOException;
}
