package com.novoda.pxhunter.task;

public class NoCustomMetadata extends Metadata<Void> {

    public NoCustomMetadata(String url) {
        super(url);
    }

    public Void getMetadata() {
        return null;
    }

}
