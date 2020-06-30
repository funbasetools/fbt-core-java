package com.funbasetools.io;

public interface ObjectLister<O> {

    ResourceStream<O> listObjects();

    ResourceStream<String> listObjectNames();
}
