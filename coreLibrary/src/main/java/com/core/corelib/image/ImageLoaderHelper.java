package com.core.corelib.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;

/**
 * Created by admin on 16/1/6.
 */
public class ImageLoaderHelper {


    public static void init(Context context) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_stub) // resource or drawable
//                .showImageForEmptyUri(R.drawable.ic_empty) // resource or drawable
//                .showImageOnFail(R.drawable.ic_error) // resource or drawable
                .considerExifParams(false)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .displayer(new SimpleBitmapDisplayer())
                .handler(new Handler())
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(1000)
                .cacheInMemory(false) // default
                .cacheOnDisk(true) // default
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(config);
    }

    public static boolean isInit() {
        return ImageLoader.getInstance().isInited();
    }

    public static void displayImage(String uri, ImageView imageView) {

        ImageLoader.getInstance().displayImage(uri, imageView);
    }

    public static void displayImage(String uri, ImageView imageView, DisplayImageOptions options) {

        ImageLoader.getInstance().displayImage(uri, imageView, options);
    }

    public static void displayImage(String uri, ImageView imageView, ImageSize imageSize) {

        ImageLoader.getInstance().displayImage(uri, imageView, imageSize);
    }

    public static void displayImage(String uri, ImageView imageView, ImageLoadingListener listener) {

        ImageLoader.getInstance().displayImage(uri, imageView, listener);
    }

    public static void displayImage(String uri, ImageView imageView, DisplayImageOptions option, ImageLoadingListener listener) {

        ImageLoader.getInstance().displayImage(uri, imageView, option, listener);
    }

    public static void displayImage(File file, ImageView imageView) {

        ImageLoader.getInstance().displayImage(Uri.fromFile(file).toString(), imageView);
    }

    public static void displayImage(File file, ImageView imageView, ImageLoadingListener listener) {

        ImageLoader.getInstance().displayImage(Uri.fromFile(file).toString(), imageView, listener);
    }

}
