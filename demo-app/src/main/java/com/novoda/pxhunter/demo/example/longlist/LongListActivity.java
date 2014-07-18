package com.novoda.pxhunter.demo.example.longlist;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.novoda.pxhunter.demo.R;
import com.novoda.pxhunter.demo.model.Image;

import java.util.List;

public class LongListActivity extends Activity {

    private ListView imageListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_list);

        imageListView = (ListView) findViewById(R.id.image_list);
        updateAdapterWithImages();
    }

    private void updateAdapterWithImages() {
        LongListImageProvider.newInstance(new LongListImageProvider.Callbacks() {

            @Override
            public void onFetched(List<Image> images) {
                ListAdapter adapter = new LongListAdapter(images, getLayoutInflater());
                imageListView.setAdapter(adapter);
            }

        }).fetchImagesAsync();
    }

}
