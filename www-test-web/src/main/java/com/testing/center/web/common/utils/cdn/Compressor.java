package com.testing.center.web.common.utils.cdn;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * CND压缩机
 */
public class Compressor {
    private Charset _encoding;


    public Compressor(Charset _encoding) {
        this._encoding = _encoding;
    }
    public Compressor() {
        this._encoding = Charset.forName("GB18030");
    }
    public byte[] encode(String text) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (DeflaterOutputStream zip = new DeflaterOutputStream(out)) {
                byte[] bin = text.getBytes(_encoding);
                zip.write(bin);
            }
            byte[] ret = out.toByteArray();
            return ret;
        }
    }

    public String decode(byte[] bytes) throws IOException {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (ByteArrayInputStream in = new ByteArrayInputStream(bytes)) {
                try (InflaterInputStream unzip = new InflaterInputStream(in)) {
                    byte[] buffer = new byte[256];
                    int n;
                    while ((n = unzip.read(buffer)) >= 0) {
                        out.write(buffer, 0, n);
                    }
                    return new String(out.toByteArray(), _encoding);
                }
            }
        }
    }
}

