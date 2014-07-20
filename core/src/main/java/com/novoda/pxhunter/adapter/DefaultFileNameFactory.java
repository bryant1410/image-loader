package com.novoda.pxhunter.adapter;

import com.novoda.pxhunter.port.FileNameFactory;

public class DefaultFileNameFactory implements FileNameFactory<Void> {

    @Override
    public String getFileName(String sourceUrl, Void metadata) {
        return sourceUrl;
    }

    @Override
    public boolean hasMetadata(String file) {
        return false;
    }

    @Override
    public Void extractMetadata(String fileName) {
        return null;
    }

}
