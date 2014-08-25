package com.novoda.pxfetcher.demo.model;

public class Image {

    private final long id;
    private final String url;
    private final String caption;

    Image(long id, String url, String caption) {
        this.id = id;
        this.url = url;
        this.caption = caption;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getCaption() {
        return caption;
    }

    @Override
    public String toString() {
        return Image.class.getName() + ".id: " + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Image image = (Image) o;

        if (!caption.equals(image.caption)) {
            return false;
        }

        if (id != image.id) {
            return false;
        }

        return url.equals(image.url);
    }

    @Override
    public int hashCode() {
        int result = ((Long) (id)).hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + caption.hashCode();
        return result;
    }

}
