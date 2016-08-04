package com.core.corelib.util;


import com.core.common.async.AsyncExecutor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 16/1/7.
 */
public class MD5UtilForMultiPartFile {

    public static void md5FileMultiPartFile(MD5CallbackForMultiPartFile callbackForMultiPartFile) {
        Md5AsyncExecutorMultiPart.getDefaultExecutor().execute(new Md5WorkerForMultiPartFile(callbackForMultiPartFile));
    }

    public static class Md5AsyncExecutorMultiPart extends AsyncExecutor {

        private static Md5AsyncExecutorMultiPart mDefault;

        private Md5AsyncExecutorMultiPart() {
            super();
        }

        public static Md5AsyncExecutorMultiPart getDefaultExecutor() {
            if (mDefault == null) {
                synchronized (Md5AsyncExecutorMultiPart.class) {
                    if (mDefault == null) {
                        mDefault = new Md5AsyncExecutorMultiPart();
                    }
                }
            }
            return mDefault;
        }
    }


    public abstract static class MD5CallbackForMultiPartFile {

        private List<MultiPartFile> multiPartFileList = new ArrayList<>();

        private MD5CallbackForMultiPartFile() {}

        public MD5CallbackForMultiPartFile(List<MultiPartFile> multiPartFiles) {
            this.multiPartFileList.clear();
            this.multiPartFileList.addAll(multiPartFiles);
        }

        public abstract void onResult(List<MultiPartFile> multiPartFiles);

        public abstract void onError(Exception e);
    }

    private static class Md5WorkerForMultiPartFile extends AsyncExecutor.Worker<List<MultiPartFile>>{

        private MD5CallbackForMultiPartFile callbackForMultiPartFile;

        public Md5WorkerForMultiPartFile(MD5CallbackForMultiPartFile callbackForMultiPartFile) {
            this.callbackForMultiPartFile = callbackForMultiPartFile;
        }

        @Override
        protected List<MultiPartFile> doInBackground() {
            for (MultiPartFile file : callbackForMultiPartFile.multiPartFileList) {
                file.md5 = MD5UtilForAndroid.getMD5(file.file);
            }
            return callbackForMultiPartFile.multiPartFileList;
        }

        @Override
        protected void abort() {
            super.abort();
            callbackForMultiPartFile.onError(new RuntimeException("calculate md5 value process be abort"));
        }

        @Override
        protected void onPostExecute(List<MultiPartFile> data) {
            super.onPostExecute(data);
            callbackForMultiPartFile.onResult(data);
        }

        @Override
        protected void onCanceled() {
            super.onCanceled();
            callbackForMultiPartFile.onError(new RuntimeException("calculate md5 value process be canceled"));
        }
    }

    public static class MultiPartFile {

        public File file;
        public String md5;
    }
}
