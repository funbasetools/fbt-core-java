package com.funbasetools.codecs.zip;

import com.funbasetools.codecs.BinaryEncoder;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class GZipEncoder implements BinaryEncoder {

    @Override
    public void encode(InputStream inputStream, OutputStream outputStream) throws IOException {
        final GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
        IOUtils.copy(inputStream, gzipOutputStream);
        gzipOutputStream.finish();
    }
}
