package com.core.corelib.svg;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.widget.ImageView;

import com.larvalabs.svgandroid.SVGBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by admin on 16/3/11.
 */
public final class SvgHelper {

    private static SvgHelper singleton;

    private Context context;

    private SvgHelper(Context context) {
        this.context = context;
    }

    public static SvgHelper with(Context context) {
        if (singleton == null) {
            synchronized (SvgHelper.class) {
                if (singleton == null) {
                    singleton = new SvgHelper(context);
                }
            }
        }
        return singleton;
    }

    public Creator loadFromResource(int resId) {

        SVGBuilder builder = new SVGBuilder().readFromResource(this.context.getResources(), resId);
        return new Creator(builder);
    }

    public Creator loadFromAsset(String svgPath) throws IOException{

        SVGBuilder builder = new SVGBuilder().readFromAsset(this.context.getAssets(), svgPath);
        return new Creator(builder);
    }

    public Creator loadFromInputStream(InputStream inputStream) {

        SVGBuilder builder = new SVGBuilder().readFromInputStream(inputStream);
        return new Creator(builder);
    }

    public final static class Creator{

        private SVGBuilder builder;

        private Creator(SVGBuilder svgBuilder) {
            this.builder = svgBuilder;
        }

        public void into(ImageView imageView) {
            if (imageView == null) {
                throw new IllegalArgumentException("imageView must not be null.");
            }

            PictureDrawable drawable = builder.build().getDrawable();
            imageView.setImageDrawable(drawable);
        }
    }
}
