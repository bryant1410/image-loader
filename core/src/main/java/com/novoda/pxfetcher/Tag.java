package com.novoda.pxfetcher;

public class Tag {
    private final String sourceUrl;
    private final String savedUrl;

    public Tag(String sourceUrl, String savedUrl) {
        this.sourceUrl = sourceUrl;
        this.savedUrl = savedUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }

        Tag tag = (Tag) o;

        return sourceUrl.equals(tag.sourceUrl);
    }

    @Override
    public int hashCode() {
        return sourceUrl.hashCode();
    }
}
