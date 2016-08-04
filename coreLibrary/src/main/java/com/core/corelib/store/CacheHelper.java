package com.core.corelib.store;

import android.content.Context;
import android.os.Environment;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by admin on 15/12/16.
 */
public class CacheHelper {

    private static final String DISK_CACHE_FILE = "diskcache";
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB

    private CacheHelper(){}

    private static DiskLruCache mCache;

    public static void init(Context context) {
        init(context, 1);
    }

    public static void init(Context context, int appVersion) {
        init(context, 1, DISK_CACHE_SIZE);
    }

    public static void init(Context context, int appVersion, int maxSize) {
        try {
            mCache = DiskLruCache.open(getDiskCacheDir(context, DISK_CACHE_FILE), appVersion, 1, maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void putString(String cacheKey, String value){

        if (mCache == null) throw new IllegalStateException("Must call openCache() first!");

        try {
            DiskLruCache.Editor editor = mCache.edit(hashKeyForDisk(cacheKey));
            if (editor != null) {

                OutputStream o = editor.newOutputStream(0);
                o.write(value.getBytes());
                editor.commit();
                mCache.flush();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getString(String cacheKey) {
        if (mCache == null) throw new IllegalStateException("Must call openCache() first!");

        try {

            DiskLruCache.Snapshot snapshot = mCache.get(hashKeyForDisk(cacheKey));
            if (snapshot != null) {
                return snapshot.getString(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static boolean remove(String cacheKey) throws IOException{
        if (mCache == null) throw new IllegalStateException("Must call openCache() first!");

        return mCache.remove(hashKeyForDisk(cacheKey));
    }

    private static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && !Environment.isExternalStorageRemovable()) {

            cachePath = context.getExternalCacheDir().getPath();  // /mnt/sdcard/Android/data/package/cache
        } else {

            cachePath = context.getCacheDir().getPath();  // /data/data/package/cache
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    private static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }


}
