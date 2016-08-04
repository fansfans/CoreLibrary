package com.core.corelib.store;

import android.content.Context;
import android.os.Environment;

import com.core.common.utils.FileTypeUtil;

import java.io.File;

/**
 * Created by admin on 16/7/14.
 */
public class StorageHelper {

    private static String packageName = null;

    public static void init(Context context) {
        packageName = context.getPackageName();
    }

    public static File getDownloadFileDir(String destFileName) {

        if (packageName == null) {
            throw new NullPointerException("the packageName can not be null, did you have invoke init() ?");
        }

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && !Environment.isExternalStorageRemovable()) {

            return Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS + File.separator
                            + packageName + File.separator + FileTypeUtil.getFileTypeCharsquence(destFileName));
        } else {

            return null;
        }
    }

    public static String getDownloadFileDirPath(String destFileName) {

        File file = getDownloadFileDir(destFileName);
        if (file != null) {
            return file.getAbsolutePath();
        } else {
            return null;
        }
    }

    public static File getDownloadFile(String destFileName) {

        if (getDownloadFileDir(destFileName) == null) {
            return null;
        }
        File file = new File(getDownloadFileDir(destFileName), destFileName);
        return file;
    }

    public static File getSearchFile(String relativePath) {

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && !Environment.isExternalStorageRemovable()) {

            return Environment.getExternalStoragePublicDirectory(relativePath);
        } else {

            return null;
        }
    }

    public static String getSearchFileAbsolutePath(String relativePath) {

        File file = getSearchFile(relativePath);
        if (file != null) {
            return file.getAbsolutePath();
        } else {
            return null;
        }
    }
}
