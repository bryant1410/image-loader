package com.novoda.pxhunter.port;

public interface FileNameFactory<T> {

    String getFileName(String sourceUrl, T metadata);

    boolean hasMetadata(String file);

    T extractMetadata(String fileName);

}
