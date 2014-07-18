package com.novoda.pxhunter.port;

import android.graphics.Bitmap;

public interface Cacher {

    Bitmap get(String id);

    void put(String id, Bitmap bitmap);

    void remove(String id);

    void clean();

}
