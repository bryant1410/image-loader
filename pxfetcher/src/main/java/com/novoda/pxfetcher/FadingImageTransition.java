package com.novoda.pxfetcher;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

class FadingImageTransition implements ImageTransition {

    private final Drawable oldDrawable;
    private final Drawable newDrawable;
    private final int animationDurationMs;

    FadingImageTransition(Drawable oldDrawable, Drawable newDrawable, int animationDurationMs) {
        this.oldDrawable = oldDrawable;
        this.newDrawable = newDrawable;
        this.animationDurationMs = animationDurationMs;
    }

    @Override
    public void startTransition(ImageView imageView) {
        TransitionDrawable transition = new TransitionDrawable(new Drawable[]{oldDrawable, newDrawable});
        transition.setCrossFadeEnabled(true);
        transition.startTransition(animationDurationMs);

        imageView.setImageDrawable(transition);
    }
}
