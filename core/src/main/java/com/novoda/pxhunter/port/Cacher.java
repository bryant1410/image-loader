package com.novoda.pxhunter.port;

import java.io.IOException;

public interface Cacher<T> {

    T get(String url) throws CachedItemNotFoundException;

    void put(String url, T data) throws UnableToCacheItemException;

    void remove(String url);

    void clean();

    class CachedItemNotFoundException extends IOException {

        public CachedItemNotFoundException(Throwable e) {
            super(e);
        }

    }

    class UnableToCacheItemException extends IOException {

        public UnableToCacheItemException(Throwable e) {
            super(e);
        }

    }

}
