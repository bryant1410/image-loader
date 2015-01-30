package com.novoda.pxfetcher;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.widget.ImageView;

import com.novoda.pxfetcher.task.Failure;
import com.novoda.pxfetcher.task.Success;

import java.lang.ref.WeakReference;

import static com.novoda.pxfetcher.FileRetriever.FileRetrieverSuccess;
import static com.novoda.pxfetcher.MemoryRetriever.MemoryRetrieverSuccess;

public class DebugImageViewCallback implements BitmapLoader.Callback {

    private final WeakReference<ImageView> imageViewWeakReference;
    private final int errorResourceId;
    private final ImageSetter imageSetter;

    public DebugImageViewCallback(ImageView imageView, int errorResourceId, ImageSetter imageSetter) {
        this.imageViewWeakReference = new WeakReference<ImageView>(imageView);
        this.errorResourceId = errorResourceId;
        this.imageSetter = imageSetter;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onResult(Success ok) {
        ImageView imageView = imageViewWeakReference.get();
        if (imageView == null) {
            return;
        }

        int color = getColorForResult(ok, imageView.getResources());
        imageView.setColorFilter(color, PorterDuff.Mode.SRC_OVER);
        imageSetter.setBitmap(imageView, ok.getBitmap());
    }

    private int getColorForResult(Success ok, Resources resources) {
        int color;
        if (ok instanceof MemoryRetrieverSuccess) {
            color = resources.getColor(R.color.debug_imageloader_memory_overlay);
        } else if (ok instanceof FileRetrieverSuccess) {
            color = resources.getColor(R.color.debug_imageloader_file_overlay);
        } else {
            color = resources.getColor(R.color.debug_imageloader_network_overlay);
        }
        return color;
    }

    @Override
    public void onResult(Failure ko) {
        ImageView imageView = imageViewWeakReference.get();
        if (imageView == null) {
            return;
        }
        showError(imageView);
    }

    protected void showError(ImageView imageView) {
        imageView.setImageResource(errorResourceId);
    }

}
