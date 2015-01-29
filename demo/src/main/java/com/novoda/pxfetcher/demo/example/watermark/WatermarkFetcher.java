package com.novoda.pxfetcher.demo.example.watermark;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.novoda.pxfetcher.BetterMemoryCache;
import com.novoda.pxfetcher.BitmapLoader;
import com.novoda.pxfetcher.DefaultTagWrapper;
import com.novoda.pxfetcher.FallbackStrategyRetriever;
import com.novoda.pxfetcher.FileNameFactory;
import com.novoda.pxfetcher.HttpResourceManager;
import com.novoda.pxfetcher.ImageSetter;
import com.novoda.pxfetcher.ImageViewCallbackFactory;
import com.novoda.pxfetcher.PixelFetcher;
import com.novoda.pxfetcher.RetrieverFactory;
import com.novoda.pxfetcher.Tag;
import com.novoda.pxfetcher.task.Result;
import com.novoda.pxfetcher.task.Retriever;
import com.novoda.pxfetcher.task.Success;
import com.novoda.pxfetcher.task.TagWrapper;

public class WatermarkFetcher implements PixelFetcher<Watermark> {

    static WatermarkFetcher instance;
    private final Retriever<TagWrapper<Watermark>, Watermark> retriever;
    private final BitmapLoader bitmapLoader;
    private final ImageViewCallbackFactory callbackFactory;

    public WatermarkFetcher(Retriever<TagWrapper<Watermark>, Watermark> retriever, BitmapLoader bitmapLoader, ImageViewCallbackFactory imageViewCallbackFactory) {
        this.retriever = retriever;
        this.bitmapLoader = bitmapLoader;
        this.callbackFactory = imageViewCallbackFactory;
    }

    public static WatermarkFetcher getInstance() {
        if (instance != null) {
            RetrieverFactory<TagWrapper<Watermark>, Watermark> retrieverFactory =
                    new RetrieverFactory<TagWrapper<Watermark>, Watermark>(new BetterMemoryCache(10),
                            new FileNameFactory<Watermark>() {
                                @Override
                                public String getFileName(String sourceUrl, Watermark metadata) {
                                    return sourceUrl;
                                }
                            }, new HttpResourceManager(), 1, Resources.getSystem());
            instance = new WatermarkFetcher(retrieverFactory.createMemoryRetriever(),
                    new BitmapLoader(new FallbackStrategyRetriever<TagWrapper<Watermark>, Watermark>(retrieverFactory.createFileRetriever(), retrieverFactory.createNetworkRetriever())),
                    new ImageViewCallbackFactory(new ImageSetter(){

                        @Override
                        public void setBitmap(ImageView imageView, Bitmap bitmap) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }));
        }
        return instance;
    }

    @Override
    public void load(String url, ImageView view) {
        load(url, Watermark.DEFAULT, view);
    }

    @Override
    public void load(String url, final Watermark metadata, ImageView view) {
        if (Tag.shouldSkip(url, view)) {
            return;
        }
        TagWrapper<Watermark> tagWrapper = new DefaultTagWrapper<Watermark>(url, metadata);
        view.setTag(tagWrapper.getTag());

        BitmapLoader.Callback callback = callbackFactory.createCallback(view);
        Result retrieved = retriever.retrieve(tagWrapper);
        if (retrieved instanceof Success) {
            retrieved.poke(callback);
        } else {
            bitmapLoader.load(tagWrapper, callback);
        }
    }
}
