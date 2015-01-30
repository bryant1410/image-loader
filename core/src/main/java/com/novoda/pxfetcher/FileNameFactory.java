package com.novoda.pxfetcher;

public interface FileNameFactory<T> {

    String getFileName(String sourceUrl, T metadata);

    T extractMetadata(String fileName);

    boolean hasMetadata(String file);

    String getCacheDirectory();

}
