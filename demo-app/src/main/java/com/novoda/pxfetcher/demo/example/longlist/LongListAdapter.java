package com.novoda.pxfetcher.demo.example.longlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.novoda.pxfetcher.demo.R;
import com.novoda.pxfetcher.demo.model.Image;

import java.util.List;

class LongListAdapter extends BaseAdapter {

    private final List<Image> images;
    private final LayoutInflater layoutInflater;

    LongListAdapter(List<Image> images, LayoutInflater layoutInflater) {
        this.images = images;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Image getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        Image image = images.get(position);
        return image.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.view_long_list_item, parent, false);
        }
        Image image = getItem(position);
        ((LongListItemView) view).updateWith(image);
        return view;
    }

}
