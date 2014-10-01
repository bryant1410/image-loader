package com.novoda.pxfetcher.demo.model;

import com.google.gson.annotations.SerializedName;

public class GsonGallery {

    @SerializedName("id")
    final String id;

    @SerializedName("link")
    final String url;

    @SerializedName("title")
    final String caption;

    public GsonGallery(String id, String url, String caption) {
        this.id = id;
        this.url = url;
        this.caption = caption;
    }

}
