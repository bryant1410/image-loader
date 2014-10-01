package com.novoda.pxfetcher.demo.model;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ImageParser {

    private final Gson gson;

    public static ImageParser newInstance() {
        return new ImageParser(new Gson());
    }

    ImageParser(Gson gson) {
        this.gson = gson;
    }

    public List<Image> parse(InputStream inputStream) {
        List<Image> images = new ArrayList<Image>();
        GsonGalleryResponse gsonGalleryResponse = gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), GsonGalleryResponse.class);

        List<GsonGalleryItem> gsonGalleryItems = gsonGalleryResponse.data;
        for (GsonGalleryItem gsonGalleryItem : gsonGalleryItems) {
            if (!gsonGalleryItem.isAlbum) {
                images.add(imageFrom(gsonGalleryItem));
            }
        }

        return images;
    }

    private Image imageFrom(GsonGalleryItem gsonGalleryItem) {
        return new Image(gsonGalleryItem.id.hashCode(), gsonGalleryItem.link, gsonGalleryItem.title);
    }

}
