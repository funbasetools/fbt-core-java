package com.funbasetools.codecs;

@FunctionalInterface
public interface Decoder<SOURCE, TARGET> {
    TARGET decode(final SOURCE src);
}
