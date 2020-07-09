module fbt.core.java {
    requires static lombok;

    requires org.apache.commons.io;
    requires org.apache.commons.lang3;

    exports com.funbasetools;
    exports com.funbasetools.codecs;
    exports com.funbasetools.codecs.text;
    exports com.funbasetools.codecs.zip;
    exports com.funbasetools.collections;
    exports com.funbasetools.id;
    exports com.funbasetools.io;
    exports com.funbasetools.network;
    exports com.funbasetools.pipes;
    exports com.funbasetools.security;
    exports com.funbasetools.text.drawing;
    exports com.funbasetools.text.drawing.tables;
}
