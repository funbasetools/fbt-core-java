package com.funbasetools.codecs.zip;

import com.funbasetools.Try;
import com.funbasetools.codecs.ToBinaryCollectionEncoder;
import com.funbasetools.collections.Stream;
import com.funbasetools.collections.Streams;
import lombok.Builder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipEncoder implements ToBinaryCollectionEncoder<Pair<String, InputStream>> {

    private final int deflateLevel;
    private final int method;

    @Builder
    private ZipEncoder(final Integer deflateLevel, final Integer method) {
        this.deflateLevel = Optional.ofNullable(deflateLevel).orElse(Deflater.DEFAULT_COMPRESSION);
        this.method = Optional.ofNullable(method).orElse(Deflater.DEFLATED);
    }

    @Override
    public void encodeTo(final Stream<Pair<String, InputStream>> entries, final OutputStream outputStream) throws IOException {
        final ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        zipOutputStream.setLevel(deflateLevel);
        zipOutputStream.setMethod(method);

        entries
            .throwingForEach(entry -> encodeEntry(entry.getKey(), entry.getValue(), zipOutputStream));

        zipOutputStream.flush();
    }

    @SafeVarargs
    public final byte[] encode(final Pair<String, byte[]>... entries) {
        return encode(Streams.of(entries));
    }

    public byte[] encode(final Stream<Pair<String, byte[]>> entries) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Try.of(() -> {
            final Stream<Pair<String, InputStream>> mappedEntries = entries
                .map(entry -> Pair.of(entry.getKey(), new ByteArrayInputStream(entry.getValue())));

            encodeTo(mappedEntries, byteArrayOutputStream);
        });

        return byteArrayOutputStream.toByteArray();
    }

    protected void encodeEntry(
        final String key,
        final InputStream entryContent,
        final ZipOutputStream zipOutputStream) throws IOException {

        zipOutputStream.putNextEntry(new ZipEntry(key));
        IOUtils.copyLarge(entryContent, zipOutputStream);
        zipOutputStream.closeEntry();
    }
}
