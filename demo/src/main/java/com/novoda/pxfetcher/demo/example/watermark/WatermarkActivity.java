package com.novoda.pxfetcher.demo.example.watermark;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.novoda.pxfetcher.demo.R;
import com.novoda.pxfetcher.demo.example.longlist.*;
import com.novoda.pxfetcher.demo.model.Image;
import com.novoda.pxfetcher.demo.model.ImageParser;
import com.novoda.pxfetcher.demo.model.UpdateAdapterViewWithMockDataTask;

import java.util.List;

public class WatermarkActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_list);

        listView = (ListView) findViewById(R.id.image_list);
        new UpdateAdapterViewWithMockDataTask(getAssets(), ImageParser.newInstance()) {
            @Override
            protected void onPostExecute(List<Image> images) {
                LongListAdapter adapter = new LongListAdapter(images, getLayoutInflater()) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = convertView;
                        if (view == null) {
                            view = getLayoutInflater().inflate(R.layout.view_long_list_item, parent, false);
                        }
                        Image image = getItem(position);
                        WatermarkFetcher.getInstance().load(image.getUrl(), new Watermark(image.getCaption()), (android.widget.ImageView) view);
                        return view;
                    }
                };
                listView.setAdapter(adapter);
            }
        }.execute();
    }

}
