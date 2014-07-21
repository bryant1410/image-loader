package com.novoda.pxhunter.demo.example.longlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novoda.pxhunter.demo.DemoApplication;
import com.novoda.pxhunter.demo.R;
import com.novoda.pxhunter.demo.model.Image;
import com.novoda.pxhunter.port.PxHunter;

public class LongListItemView extends LinearLayout {

    private ImageView imageView;
    private TextView captionTextView;

    public LongListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LongListItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        layoutInflater.inflate(R.layout.merge_long_list_item, this, true);

        imageView = (ImageView) findViewById(R.id.long_list_item_thumb_image);
        captionTextView = (TextView) findViewById(R.id.long_list_item_caption_text);
    }

    public void updateWith(Image image) {
        captionTextView.setText(image.getCaption());
        String url = image.getUrl();

        // Get a reference to a PxHunter, then load the url into the imageView
        PxHunter<Void> pxHunter = DemoApplication.pxHunter();
        // TODO: @ben, don't want to pass null - multiple methods?
        pxHunter.load(url, imageView, null);
    }

}
