package com.novoda.pxfetcher.demo.example.longlist;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.novoda.pxfetcher.PixelFetchers;
import com.novoda.pxfetcher.demo.R;
import com.novoda.pxfetcher.demo.model.Image;
import com.novoda.pxfetcher.demo.model.ImageParser;
import com.novoda.pxfetcher.demo.model.UpdateAdapterViewWithMockDataTask;

import java.util.List;

public class LongListActivity extends Activity {

    private ListView imageListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_list);

        imageListView = (ListView) findViewById(R.id.image_list);

        // initialize PixelFetchers
        PixelFetchers.getInstance(this);

        new UpdateAdapterViewWithMockDataTask(getAssets(), ImageParser.newInstance()) {
            @Override
            protected void onPostExecute(List<Image> images) {
                ListAdapter adapter = new LongListAdapter(images, getLayoutInflater());
                imageListView.setAdapter(adapter);
            }
        }.execute();
    }

}
