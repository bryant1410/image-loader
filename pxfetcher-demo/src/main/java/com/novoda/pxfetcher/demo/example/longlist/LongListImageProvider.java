package com.novoda.pxfetcher.demo.example.longlist;

import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.novoda.pxfetcher.demo.DemoApplication;
import com.novoda.pxfetcher.demo.model.Image;
import com.novoda.pxfetcher.demo.model.ImageParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class LongListImageProvider {

    private final FetchImageDataTask fetchImageDataTask;

    public static LongListImageProvider newInstance(Callbacks callbacks) {
        return new LongListImageProvider(FetchImageDataTask.newInstance(callbacks));
    }

    LongListImageProvider(FetchImageDataTask fetchImageDataTask) {
        this.fetchImageDataTask = fetchImageDataTask;
    }

    public void fetchImagesAsync() {
        fetchImageDataTask.execute();
    }

    public interface Callbacks {

        void onFetched(List<Image> images);

    }

    private static class FetchImageDataTask extends AsyncTask<Void, Void, List<Image>> {

        private static final String MOCK_JSON_GALLERY = "mocks/gallery.json";

        private final AssetManager assetManager;
        private final ImageParser imageParser;
        private final Callbacks callbacks;

        private FetchImageDataTask(AssetManager assetManager, ImageParser imageParser, Callbacks callbacks) {
            this.assetManager = assetManager;
            this.imageParser = imageParser;
            this.callbacks = callbacks;
        }

        public static FetchImageDataTask newInstance(Callbacks callbacks) {
            AssetManager assetManager = DemoApplication.context().getAssets();
            ImageParser imageParser = ImageParser.newInstance();

            return new FetchImageDataTask(assetManager, imageParser, callbacks);
        }

        @Override
        protected List<Image> doInBackground(Void... params) {
            InputStream inputStream = openAsset(MOCK_JSON_GALLERY);
            return imageParser.parse(inputStream);
        }

        private InputStream openAsset(String path) {
            try {
                return assetManager.open(path);
            } catch (IOException e) {
                return new InputStream() {

                    @Override
                    public int read() throws IOException {
                        return 0;
                    }

                };
            }
        }

        @Override
        protected void onPostExecute(List<Image> images) {
            callbacks.onFetched(images);
        }

    }

}
