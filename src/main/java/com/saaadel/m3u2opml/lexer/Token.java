package com.saaadel.m3u2opml.lexer;

import com.saaadel.jflex.Yytoken;


public class Token implements Yytoken {
    private final int line;
    private final int column;
    private final int charOffset;
    private final Type type;
    private final Object value;

    public enum Type {
        EXTM3U,
        EXTINF,
        PATH,
        IGNORING
    }

    public Token(Type type, int yyline, int yycolumn, int yychar) {
        this(type, yyline, yycolumn, yychar, null);
    }

    public Token(Type type, int yyline, int yycolumn, int yychar, Object value) {
        this.type = type;
        this.line = yyline + 1;
        this.column = yycolumn + 1;
        this.charOffset = yychar;
        this.value = value;
    }

    public String getStringValue() {
        return value == null ? null : value.toString();
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getCharOffset() {
        return charOffset;
    }

    public Type getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "line=" + line +
                ", column=" + column +
                ", charOffset=" + charOffset +
                ", type=" + type +
                ", value=" + value +
                '}';
    }
}
