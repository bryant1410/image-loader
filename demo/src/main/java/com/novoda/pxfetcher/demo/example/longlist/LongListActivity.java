package com.novoda.pxfetcher.demo.example.longlist;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.novoda.pxfetcher.demo.DemoApplication;
import com.novoda.pxfetcher.demo.R;
import com.novoda.pxfetcher.demo.model.Image;
import com.novoda.pxfetcher.demo.model.ImageParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class LongListActivity extends Activity {

    private ListView imageListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_list);

        imageListView = (ListView) findViewById(R.id.image_list);
        UpdateListViewWithMockDataTask.newInstance(imageListView, getLayoutInflater()).execute();
    }

    /**
     * Used to populate the ListView with some content. None of the image-loading goodness is in here.
     *
     * Check out the {@link LongListItemView} class for usage of the library.
     *
     * @see {@link LongListItemView#updateWith(Image)}
     */
    private static class UpdateListViewWithMockDataTask extends AsyncTask<Void, Void, List<Image>> {

        private static final String MOCK_JSON_GALLERY = "mocks/gallery.json";

        private final AssetManager assetManager;
        private final ImageParser imageParser;
        private final ListView listView;
        private final LayoutInflater layoutInflater;

        public static UpdateListViewWithMockDataTask newInstance(ListView listView, LayoutInflater layoutInflater) {
            AssetManager assetManager = DemoApplication.context().getAssets();
            ImageParser imageParser = ImageParser.newInstance();

            return new UpdateListViewWithMockDataTask(assetManager, imageParser, listView, layoutInflater);
        }

        UpdateListViewWithMockDataTask(AssetManager assetManager, ImageParser imageParser, ListView listView, LayoutInflater layoutInflater) {
            this.assetManager = assetManager;
            this.imageParser = imageParser;
            this.listView = listView;
            this.layoutInflater = layoutInflater;
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
            ListAdapter adapter = new LongListAdapter(images, layoutInflater);
            listView.setAdapter(adapter);
        }

    }

}
