package com.funbasetools.text;

import com.funbasetools.codecs.Decoder;

@FunctionalInterface
public interface TextDecoder extends Decoder<String, byte[]> {
}
