package com.core.corelib.gallery;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.core.corelib.image.ImageLoaderHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.io.File;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * Created by admin on 16/1/27.
 */
public class GalleryHelper {

    public static final int REQUEST_CODE_GALLERY = 1001;
    public static final int REQUEST_CODE_CAMERA = 1002;

    public static void init (Context context, boolean debug) {

        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

        cn.finalteam.galleryfinal.ImageLoader imageLoader = new UILImageLoader();
        CoreConfig.Builder builder = new CoreConfig.Builder(context, imageLoader, ThemeConfig.DARK);
        builder.setDebug(debug);
        builder.setFunctionConfig(functionConfig);

        CoreConfig coreConfig = builder.build();
        GalleryFinal.init(coreConfig);

        if (!ImageLoaderHelper.isInit()) {
            ImageLoaderHelper.init(context);
        }
    }


    public static void openGallerySingle(int requestCode, GalleryFinal.OnHanlderResultCallback callback) {
        GalleryFinal.openGallerySingle(requestCode, callback);
    }

    public static void openGallerySingle(int requestCode, FunctionConfig config, GalleryFinal.OnHanlderResultCallback callback) {
        GalleryFinal.openGallerySingle(requestCode, config, callback);
    }

    public static void openCamera(int requestCode, GalleryFinal.OnHanlderResultCallback callback){
        GalleryFinal.openCamera(requestCode, callback);
    }

    public static void openCamera(int requestCode, FunctionConfig config, GalleryFinal.OnHanlderResultCallback callback) {
        GalleryFinal.openCamera(requestCode, config, callback);
    }


    public static class UILImageLoader implements cn.finalteam.galleryfinal.ImageLoader {

        private Bitmap.Config mImageConfig;

        public UILImageLoader() {
            this(Bitmap.Config.RGB_565);
        }

        public UILImageLoader(Bitmap.Config config) {
            this.mImageConfig = config;
        }

        @Override
        public void displayImage(Activity activity, String path, GFImageView imageView, Drawable defaultDrawable, int width, int height) {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheOnDisk(false)
                    .cacheInMemory(false)
                    .bitmapConfig(mImageConfig)
                    .build();
            ImageSize imageSize = new ImageSize(width, height);
            ImageLoader.getInstance().displayImage(Uri.fromFile(new File(path)).toString(), new ImageViewAware(imageView), options, imageSize, null, null);
        }

        @Override
        public void clearMemoryCache() {

        }
    }
}
