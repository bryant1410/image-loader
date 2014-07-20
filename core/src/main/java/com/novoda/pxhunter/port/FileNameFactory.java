package com.novoda.pxhunter.port;

public interface FileNameFactory<T> {

    /**
     * Constructs a filename based on the source url of the image.
     *
     * @param sourceUrl the remote url of the image
     * @param metadata optional parameter (may be null) containing info to help construct file name
     * @return filename representing the image retrieved from sourceUrl
     */
    String getFileName(String sourceUrl, T metadata);

    // TODO: @dorvaryn - String getSourceUrl(String fileName, T metadata);

    boolean hasMetadata(String file);

    T extractMetadata(String fileName);

}
