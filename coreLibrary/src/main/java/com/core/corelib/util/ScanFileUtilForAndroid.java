package com.core.corelib.util;


import com.core.common.async.AsyncExecutor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 15/12/25.
 */
public class ScanFileUtilForAndroid {

    public static void scanFile(ScanFileCallback callback) {

        ScanFileAsyncExecutor.getDefaultAsyncExecutor().execute(new ScanFileWorker(callback));
    }

    private static class ScanFileAsyncExecutor extends AsyncExecutor {

        private static ScanFileAsyncExecutor mDefault;

        private ScanFileAsyncExecutor() {
            super();
        }

        public static ScanFileAsyncExecutor getDefaultAsyncExecutor() {
            if (mDefault == null) {
                synchronized (ScanFileAsyncExecutor.class) {
                    if (mDefault == null) {
                        mDefault = new ScanFileAsyncExecutor();
                    }
                }
            }
            return mDefault;
        }
    }

    public abstract static class ScanFileCallback{

        protected Object dirObject;
        private File dir;
        private List<String> extension = new ArrayList<>();
        private List<String> fileNameFilter = new ArrayList<>();

        private ScanFileCallback() {}

        public ScanFileCallback(File dir, List<String> extension) {
            this.dirObject = dir;
            this.dir = dir;
            this.extension.clear();
            this.extension.addAll(extension);
        }

        public ScanFileCallback(File dir, List<String> extension, List<String> fileNameFilter) {
//            this.dirObject = dir;
//            this.dir = dir;
//            this.extension.clear();
//            this.extension.addAll(extension);
            this(dir, extension);
            this.fileNameFilter.clear();
            this.fileNameFilter.addAll(fileNameFilter);
        }

        public abstract void onResult(Object dir, List<String> extension, List<File> files);

        public abstract void onError(Exception e);

    }

    public abstract static class ScanFileListCallback extends ScanFileCallback{

        private List<File> dirs = new ArrayList<>();
        private List<String> extension = new ArrayList<>();
        private List<String> fileNameFilter = new ArrayList<>();

        public ScanFileListCallback(List<File> dirs, List<String> extension) {
            this.dirObject = dirs;
            this.dirs.clear();
            this.dirs.addAll(dirs);
            this.extension.clear();
            this.extension.addAll(extension);
        }

        public ScanFileListCallback(List<File> dirs, List<String> extension, List<String> fileNameFilter) {
            this(dirs, extension);
            this.fileNameFilter.clear();
            this.fileNameFilter.addAll(fileNameFilter);
        }

    }

    private static class ScanFileWorker extends AsyncExecutor.Worker<List<File>> {

        private ScanFileCallback callback;

        public ScanFileWorker(ScanFileCallback callback) {
            this.callback = callback;
        }

        @Override
        protected List<File> doInBackground() {
            if (callback instanceof ScanFileListCallback) {
                return searchFile(((ScanFileListCallback) callback).dirs, ((ScanFileListCallback) callback).extension, ((ScanFileListCallback) callback).fileNameFilter);
            }
            return searchFile(callback.dir, callback.extension, callback.fileNameFilter);
        }

        @Override
        protected void onPostExecute(List<File> data) {
            super.onPostExecute(data);
            callback.onResult(callback.dirObject, callback.extension, data);
        }

        @Override
        protected void onCanceled() {
            super.onCanceled();
            callback.onError(new RuntimeException("scan dir process be cancel"));
        }

        @Override
        protected void abort() {
            super.abort();
            callback.onError(new RuntimeException("scan dir process be abort"));
        }
    }

    private static List<File> searchFile(File dir, List<String> extension, List<String> fileNameFilter) {

        List<File> files = new ArrayList<>();
        binarySearchFile(dir, extension, fileNameFilter, files);

        return files;
    }

    private static List<File> searchFile(List<File> dirs, List<String> extension, List<String> fileNameFilter) {
        List<File> files = new ArrayList<>();
        for (File file : dirs) {
            binarySearchFile(file, extension, fileNameFilter, files);
        }
        return files;
    }

    private static void binarySearchFile(File dir,List<String> extensions, List<String> fileNameFilter, List<File> finalFiles) {
        if (dir == null) {
            return;
        }
        if (dir.isFile()) {
            String extension = getExtensionName(dir.getAbsolutePath());
            String fileName = dir.getName();
            synchronized (extensions) {
                if (extensions.contains(extension) && isFileNameContainsWord(fileName, fileNameFilter)) {
                    finalFiles.add(dir);
                }
            }
        }
        File[] dirFiles = dir.listFiles();
        if (dirFiles != null) {

            for (File file : dirFiles) {
                binarySearchFile(file, extensions, fileNameFilter, finalFiles);
            }
        }
    }

    private static boolean isFileNameContainsWord(String fileName, List<String> fileNameFilter) {

        for (String filter : fileNameFilter) {
            if (fileName.contains(filter)) {
                return true;
            }
        }
        return false;
    }

    /***
     * 获取文件扩展名
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
        return filename;
    }
}
