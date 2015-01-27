package com.novoda.pxfetcher.demo.model;

import com.google.gson.annotations.SerializedName;

class GsonGalleryItem {

    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    @SerializedName("link")
    String link;

    @SerializedName("is_album")
    boolean isAlbum;

}