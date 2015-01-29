package com.novoda.pxfetcher.demo.example.twosizes.watermark;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.novoda.pxfetcher.demo.R;
import com.novoda.pxfetcher.demo.example.longlist.LongListAdapter;
import com.novoda.pxfetcher.demo.model.Image;
import com.novoda.pxfetcher.demo.model.ImageParser;
import com.novoda.pxfetcher.demo.model.UpdateAdapterViewWithMockDataTask;

import java.util.List;

public class TwoSizesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_lists);

        final ListView listView1 = (ListView) findViewById(R.id.image_list1);
        final ListView listView2 = (ListView) findViewById(R.id.image_list2);
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
                        ImageSizeFetcher.getInstance().load(image.getUrl(), ImageSize.BIG, (android.widget.ImageView) view);
                        return view;
                    }
                };
                listView1.setAdapter(adapter);

                adapter = new LongListAdapter(images, getLayoutInflater()) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = convertView;
                        if (view == null) {
                            view = getLayoutInflater().inflate(R.layout.view_long_list_item, parent, false);
                        }
                        Image image = getItem(position);
                        ImageSizeFetcher.getInstance().load(image.getUrl(), ImageSize.SMALL, (android.widget.ImageView) view);
                        return view;
                    }
                };
                listView2.setAdapter(adapter);
            }
        }.execute();
    }

}
