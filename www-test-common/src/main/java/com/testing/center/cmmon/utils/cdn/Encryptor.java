package com.testing.center.cmmon.utils.cdn;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 * CND压缩和解密
 */
public class Encryptor {
    private byte[] _key;
    private Compressor _compressor;

    public Encryptor(byte[] key, Charset _encoding) {
        _key = key;
        _compressor = new Compressor(_encoding);
    }

    private void mix(byte[] input) {
        int n = _key.length;
        int len = input.length;
        for (int i = 0, j = 0; i < len; i++, j++) {
            if (j >= n) {
                j = 0;
            }
            input[i] = (byte) (input[i] ^ _key[j]);
        }
    }

    public byte[] encode(String text) throws IOException {
        byte[] ret = _compressor.encode(text);
        mix(ret);
        return ret;
    }

    public String decode(byte[] bytes) throws IOException {
        mix(bytes);
        return _compressor.decode(bytes);
    }


}