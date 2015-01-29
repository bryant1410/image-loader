package com.novoda.pxfetcher.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.novoda.pxfetcher.demo.example.longlist.LongListActivity;
import com.novoda.pxfetcher.demo.example.twosizes.watermark.TwoSizesActivity;
import com.novoda.pxfetcher.demo.example.watermark.WatermarkActivity;

public class DemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        Button listActivityButton = (Button) findViewById(R.id.list_button);
        listActivityButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent listActivity = new Intent(DemoActivity.this, LongListActivity.class);
                startActivity(listActivity);
            }

        });

        Button twoSizesActivityButton = (Button) findViewById(R.id.two_lists_button);
        twoSizesActivityButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent listActivity = new Intent(DemoActivity.this, TwoSizesActivity.class);
                startActivity(listActivity);
            }

        });

        Button watermarkActivityButton = (Button) findViewById(R.id.watermark_list_button);
        watermarkActivityButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent listActivity = new Intent(DemoActivity.this, WatermarkActivity.class);
                startActivity(listActivity);
            }

        });
    }

}
