package com.saaadel.m3u2opml;

import com.saaadel.jflex.GenericLexer;
import com.saaadel.jflex.Yytoken;
import com.saaadel.m3u2opml.lexer.Token;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileSystems;

public class Converter {
    private static final String UNKNOWN_CATEGORY = "Неизвестная скорость потока";

    private static <TT extends Yytoken> GenericLexer<TT> getLexer(final Reader reader) {
        try {
            @SuppressWarnings("unchecked")
            final Class<? extends GenericLexer<TT>> lexerClass = (Class<? extends GenericLexer<TT>>) Converter.class.getClassLoader().loadClass(Converter.class.getPackage().getName() + ".lexer.M3U8Lexer");
            return lexerClass.getConstructor(Reader.class).newInstance(reader);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * @param args
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Using: java -cp ... com.saaadel.m3u2opml.Converter <input-m3u8-file> <output-opml-file>");
        }
        final File inFile = new File(FileSystems.getDefault().getPath(args[0]).toUri());
        final File outFile = new File(FileSystems.getDefault().getPath(args[1]).toUri());

        try (final FileReader reader = new FileReader(inFile)) {
            final GenericLexer<Token> lexer = Converter.<Token>getLexer(reader);

            @SuppressWarnings("unused")
            final boolean outDirCreated = outFile.getParentFile().mkdirs();
            try (final FileWriter writer = new FileWriter(outFile)) {
                writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                writer.append("<opml version=\"2.0\">\n");
                writer.append("  <head>\n");
                writer.append("    <title>MusicBee Radio Station List</title>\n");
                //TODO put current date here
                writer.append("    <dateCreated>Fri, 01 Jan 2016 00:00:00 GMT</dateCreated>\n");
                writer.append("  </head>\n");
                writer.append("  <body>\n");

                String extinf = null;
                for (Token token = lexer.nextToken(); token != null; token = lexer.nextToken()) {
                    switch (token.getType()) {
                        case EXTINF:
                            extinf = token.getStringValue();
                            break;
                        case PATH:
                            writer.append("    <outline text=\"");
                            writer.append(StringEscapeUtils.escapeXml10(extinf));
                            writer.append("\" description=\"\" category=\"//");
                            writer.append(UNKNOWN_CATEGORY);
                            writer.append("/\" type=\"link\" url=\"");
                            writer.append(StringEscapeUtils.escapeXml10(token.getStringValue()));
                            writer.append("\" />\n");
                            extinf = null;
                            break;
                        default:
                    }
                }
                writer.append("  </body>\n");
                writer.append("</opml>\n");
            }
        }
        System.out.println("OK:\t\"" + inFile.getPath() + "\" converted into\n\t\"" + outFile.getPath() + "\"");
    }
}
