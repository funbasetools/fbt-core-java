package com.funbasetools.codecs;

import com.funbasetools.collections.Stream;

public interface ToBinaryCollectionEncoder<SOURCE>
    extends
    CollectionEncoder<SOURCE, byte[]>,
    ToBinaryEncoder<Stream<SOURCE>> {
}
