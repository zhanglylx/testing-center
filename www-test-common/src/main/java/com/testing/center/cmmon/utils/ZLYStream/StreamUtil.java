package com.testing.center.cmmon.utils.ZLYStream;

import java.nio.charset.Charset;
import java.io.File;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class StreamUtil {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static PrintWriter getCharacterPrintWriter(File file, Charset cs, boolean append) throws FileNotFoundException {
        return new PrintWriter(
                getCharacterBufferedWriter(file, cs, append), true
        );

    }

    public static PrintWriter getCharacterPrintWriter(File file, boolean append) throws FileNotFoundException {
        return getCharacterPrintWriter(file, DEFAULT_CHARSET, append);
    }

    public static PrintWriter getCharacterPrintWriter(File file) throws FileNotFoundException {
        return getCharacterPrintWriter(file, false);
    }

    public static BufferedWriter getCharacterBufferedWriter(File file, Charset cs, boolean append) throws FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file, append), cs));
    }

    public static BufferedWriter getCharacterBufferedWriter(File file) throws FileNotFoundException {
        return getCharacterBufferedWriter(file, DEFAULT_CHARSET, false);
    }

    public static BufferedReader getCharacterBufferedReader(File file, Charset cs) throws FileNotFoundException {
        return new BufferedReader(
                new InputStreamReader(new FileInputStream(file), cs)
        );
    }

    public static BufferedReader getCharacterBufferedReader(File file) throws FileNotFoundException {
        return getCharacterBufferedReader(file, DEFAULT_CHARSET);
    }

    public static void getCharacterBufferedReader(File file, BuffReaderCalls buffReaderCalls) throws IOException {
        getCharacterBufferedReader(file, DEFAULT_CHARSET, buffReaderCalls);
    }

    public static void getCharacterBufferedReader(File file, Charset cs, BuffReaderCalls buffReaderCalls) throws IOException {
        BufferedReader reader = getCharacterBufferedReader(file, cs);
        getCharacterBufferedReaderCalls(reader, buffReaderCalls);
        reader.close();
    }

    public static void getCharacterBufferedReaderCalls(BufferedReader reader, BuffReaderCalls buffReaderCalls) throws IOException {
        String s;
        while ((s = reader.readLine()) != null) {
            if (buffReaderCalls.BufferedReaderLine(s)) break;
        }
    }
}
