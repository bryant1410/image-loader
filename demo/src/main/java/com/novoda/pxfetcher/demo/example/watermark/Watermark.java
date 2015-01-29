package com.novoda.pxfetcher.demo.example.watermark;

public class Watermark {
    private String watermark;
    public static final Watermark DEFAULT = new Watermark("No copyright!");

    public Watermark(String watermark) {
        this.watermark = watermark;
    }

    public String getWatermark() {
        return watermark;
    }
}
