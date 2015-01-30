package com.novoda.pxfetcher;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.novoda.imageloader20.task.Result;
import com.novoda.imageloader20.task.Retriever;
import com.novoda.imageloader20.task.TagWrapper;
import com.novoda.notils.logger.simple.Log;

import java.io.IOException;

public class UrlSchemaAssetRetriever implements Retriever {

    private static final String FORWARD_SLASH_REPLACEMENT = "\\*";
    private static final String HTTP_SCHEMA_REPLACEMENT = "__";

    private static final String HTTP_SCHEMA = "://";
    private static final String FORWARD_SLASH = "/";

    private static final String IMAGE_LOCATION = "images/";

    private final AssetManager assetManager;
    private final BitmapProcessor processor;

    public UrlSchemaAssetRetriever(AssetManager assetManager, BitmapProcessor processor) {
        this.assetManager = assetManager;
        this.processor = processor;
    }

    @Override
    public Result retrieve(TagWrapper tagWrapper) {
        String sourceUrl = tagWrapper.getSourceUrl();

        Bitmap resultingImage = null;
        try {
            String assetUrl = IMAGE_LOCATION + convertToAssetSchema(sourceUrl);
            Bitmap decodedAssetBitmap = BitmapFactory.decodeStream(assetManager.open(assetUrl));
            resultingImage = processor.elaborate(tagWrapper, decodedAssetBitmap);
        } catch (IOException swallow) {
            Log.w("Asset loading failed for : " + sourceUrl, swallow.getLocalizedMessage());
        }
        return createResultFrom(resultingImage);
    }

    private String convertToAssetSchema(String sourceUrl) {
        return sourceUrl.replaceAll(HTTP_SCHEMA, HTTP_SCHEMA_REPLACEMENT).replaceAll(FORWARD_SLASH, FORWARD_SLASH_REPLACEMENT);
    }

    private Result createResultFrom(Bitmap resultingImage) {
        if (resultingImage == null) {
            return new Failure();
        }
        return new Success(resultingImage);
    }

    public static class Success extends com.novoda.imageloader20.task.Success {
        public Success(Bitmap bitmap) {
            super(bitmap);
        }
    }

    public static class Failure extends com.novoda.imageloader20.task.Failure {
    }

}
