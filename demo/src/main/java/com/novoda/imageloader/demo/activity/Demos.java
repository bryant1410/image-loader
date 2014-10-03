package com.novoda.imageloader.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.novoda.imageloader.demo.R;

/**
 * Nothing really interesting here just a dashboard.
 */
public class Demos extends Activity implements OnItemClickListener {

    private static final boolean LIST_VIEW = false;
    private static final boolean GRID_VIEW = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demos);
        ListView entries = (ListView) findViewById(R.id.demo_list);
        entries.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int fadeIn = R.anim.fade_in;

        switch (position) {
            case 0:
                startActivity(ImageLongList.class);
                break;
            case 1:
                startActivity(ImageLongList.class, fadeIn, LIST_VIEW);
                break;
            case 2:
                startActivity(ImageLongList.class, fadeIn, GRID_VIEW);
                break;
            case 3:
                startActivity(LongSmallImageList.class);
                break;
            case 4:
                startActivity(LongSmallImageList.class, fadeIn, LIST_VIEW);
                break;
            case 5:
                startActivity(LongSmallImageList.class, fadeIn, GRID_VIEW);
                break;
            case 6:
                startActivity(BigImages.class);
                break;
            case 7:
                startActivity(BigImages.class, fadeIn, LIST_VIEW);
                break;
            case 8:
                startActivity(DirectLoading.class);
                break;
            case 9:
                startActivity(DirectLoading.class, fadeIn, LIST_VIEW);
                break;
            default:
                break;
        }
    }

    private void startActivity(Class<? extends Activity> activity) {
        startActivity(createIntent(activity, -1, false));
    }

    private void startActivity(Class<? extends Activity> activity, int animationId, boolean isGrid) {
        startActivity(createIntent(activity, animationId, isGrid));
    }

    private Intent createIntent(Class<? extends Activity> activity, int animationId, boolean isGrid) {
        Intent intent = createActivityIntent(activity);
        intent.putExtra("animated", animationId);
        intent.putExtra("grid", isGrid);
        return intent;
    }

    private Intent createActivityIntent(Class<? extends Activity> activity) {
        return new Intent(Demos.this, activity);
    }

}
