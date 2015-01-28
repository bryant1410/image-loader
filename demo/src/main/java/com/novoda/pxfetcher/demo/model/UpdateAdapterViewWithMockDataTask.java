package com.novoda.pxfetcher.demo.model;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.widget.AdapterView;

import com.novoda.pxfetcher.demo.DemoApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Used to populate the ListView with some content. None of the image-loading goodness is in here.
 * <p/>
 * Check out the {@link com.novoda.pxfetcher.demo.example.gridview.LongListItemView} class for usage of the library.
 *
 * @see {@link com.novoda.pxfetcher.demo.example.gridview.LongListItemView#updateWith(com.novoda.pxfetcher.demo.model.Image)}
 */
public class UpdateAdapterViewWithMockDataTask extends AsyncTask<Void, Void, List<Image>> {

    private static final String MOCK_JSON_GALLERY = "mocks/gallery.json";

    private final AssetManager assetManager;
    private final ImageParser imageParser;

    public static UpdateAdapterViewWithMockDataTask newInstance() {
        AssetManager assetManager = DemoApplication.context().getAssets();
        ImageParser imageParser = ImageParser.newInstance();

        return new UpdateAdapterViewWithMockDataTask(assetManager, imageParser);
    }

    public UpdateAdapterViewWithMockDataTask(AssetManager assetManager, ImageParser imageParser) {
        this.assetManager = assetManager;
        this.imageParser = imageParser;
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

}
