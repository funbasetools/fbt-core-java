package com.funbasetools.io;

import com.funbasetools.collections.Stream;

public interface ObjectLister<O> {

    Stream<O> listObjects();

    Stream<String> listObjectNames();
}
