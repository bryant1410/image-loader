package com.novoda.pxfetcher.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.novoda.pxfetcher.demo.example.longlist.LongListActivity;

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
    }

}
