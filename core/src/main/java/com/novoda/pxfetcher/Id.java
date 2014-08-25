package com.novoda.pxfetcher;

public class Id {

    private final long id;

    public Id(long id) {
        this.id = id;
    }

    long getValue() {
        return id;
    }

    @Override
    public boolean equals(Object otherId) {
        if (this == otherId) {
            return true;
        }
        if (otherId == null || getClass() != otherId.getClass()) {
            return false;
        }
        return id == ((Id) otherId).id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

}
