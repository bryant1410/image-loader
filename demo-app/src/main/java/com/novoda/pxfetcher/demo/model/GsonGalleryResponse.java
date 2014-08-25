package com.novoda.pxfetcher.demo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class GsonGalleryResponse {

    @SerializedName("data")
    List<GsonGalleryItem> data;

    @SerializedName("success")
    boolean success;

    @SerializedName("status")
    int status;

}