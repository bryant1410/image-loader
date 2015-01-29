package com.novoda.pxfetcher.demo.example.twosizes.watermark;

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

public class ImageSizeFetcher implements PixelFetcher<ImageSize> {

    static ImageSizeFetcher instance;
    private final Retriever<TagWrapper<ImageSize>, ImageSize> retriever;
    private final BitmapLoader bitmapLoader;
    private final ImageViewCallbackFactory callbackFactory;

    public ImageSizeFetcher(Retriever<TagWrapper<ImageSize>, ImageSize> retriever, BitmapLoader bitmapLoader, ImageViewCallbackFactory imageViewCallbackFactory) {
        this.retriever = retriever;
        this.bitmapLoader = bitmapLoader;
        this.callbackFactory = imageViewCallbackFactory;
    }

    public static ImageSizeFetcher getInstance() {
        if (instance != null) {
            RetrieverFactory<TagWrapper<ImageSize>, ImageSize> retrieverFactory =
                    new RetrieverFactory<TagWrapper<ImageSize>, ImageSize>(new BetterMemoryCache(10),
                            new FileNameFactory<ImageSize>() {
                                @Override
                                public String getFileName(String sourceUrl, ImageSize metadata) {
                                    return sourceUrl;
                                }
                            }, new HttpResourceManager(), 1, Resources.getSystem());
            instance = new ImageSizeFetcher(retrieverFactory.createMemoryRetriever(),
                    new BitmapLoader(new FallbackStrategyRetriever<TagWrapper<ImageSize>, ImageSize>(retrieverFactory.createFileRetriever(), retrieverFactory.createNetworkRetriever())),
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
        load(url, ImageSize.BIG, view);
    }

    @Override
    public void load(String url, final ImageSize metadata, ImageView view) {
        if (Tag.shouldSkip(url, view)) {
            return;
        }
        TagWrapper<ImageSize> tagWrapper = new DefaultTagWrapper<ImageSize>(url, metadata);
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
