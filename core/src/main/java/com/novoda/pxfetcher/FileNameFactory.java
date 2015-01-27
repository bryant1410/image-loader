package com.novoda.pxfetcher;

public interface FileNameFactory<T> {

    String getFileName(String sourceUrl, T metadata);

}
