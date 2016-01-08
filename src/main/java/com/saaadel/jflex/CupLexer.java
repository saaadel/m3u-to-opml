package com.saaadel.jflex;

/**
 * Interface for JFlex CUP lexer
 *
 * @see %cup
 */
public interface CupLexer extends java_cup.runtime.Scanner {
    java_cup.runtime.Symbol next_token() throws java.io.IOException;
}
