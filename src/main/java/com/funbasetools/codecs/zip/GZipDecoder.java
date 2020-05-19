package com.funbasetools.codecs.zip;

import com.funbasetools.codecs.BinaryDecoder;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;

public class GZipDecoder implements BinaryDecoder {

    @Override
    public void decode(InputStream inputStream, OutputStream outputStream) throws IOException {
        final GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
        IOUtils.copy(gzipInputStream, outputStream);
    }
}
