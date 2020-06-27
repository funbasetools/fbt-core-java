package com.funbasetools.codecs;

@FunctionalInterface
public interface ToBinaryDecoder<SOURCE> extends Decoder<SOURCE, byte[]> {
}
