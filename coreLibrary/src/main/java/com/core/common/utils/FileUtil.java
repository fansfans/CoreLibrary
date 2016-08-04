package com.core.common.utils;


import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

/**
 * Created by admin on 16/1/4.
 */
public class FileUtil {

    /**
     * 获取文件名，不包含扩展名
     *
     * @param file
     * @return
     */
    public static String getFileName(File file) {

        return file.getName().substring(0, FilenameUtils.indexOfExtension(file.getName()));
    }


    /***
     * 获取文件扩展名
     *
     * @param file
     * @return 返回文件扩展名
     */
    public static String getFileExtension(File file) {

        return getExtensionName(file.getName());
    }

    /***
     * 获取文件扩展名
     *
     * @param filename
     * @return 返回文件扩展名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return null;
    }

    /***
     * 根据路径删除图片
     */
    public static boolean deleteFile(File file) {
        return file != null && file.delete();
    }

    public static void deleteFiles(File file) {

        if (file.isDirectory()) {
            for (File file1 : file.listFiles()) {
                deleteFile(file1);
            }
        }
        file.delete();
    }

    public static void deleteFilesExceptSomeInDirectory(final File dirParent, final List<File> dirChildes) {

        File [] files = dirParent.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String filename) {
                return !( dirParent.equals(dir) && dirChildes.contains(new File(dir, filename)));
            }
        });

        for (File file : files) {
            deleteFiles(file);
        }
    }
}
