package com.core.corelib.util;


import com.core.common.async.AsyncExecutor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by admin on 15/12/25.
 */
public class MD5UtilForAndroid {


    public static void md5ForFile(MD5Callback callBack) {
        Md5AsyncExecutor.getDefaultExecutor().execute(new MD5Worker(callBack));
    }

    public static class Md5AsyncExecutor extends AsyncExecutor {

        private static Md5AsyncExecutor mDefault;

        private Md5AsyncExecutor() {
            super();
        }

        public static Md5AsyncExecutor getDefaultExecutor() {
            if (mDefault == null) {
                synchronized (Md5AsyncExecutor.class) {
                    if (mDefault == null) {
                        mDefault = new Md5AsyncExecutor();
                    }
                }
            }
            return mDefault;
        }
    }


    public static abstract class MD5Callback {

        private File md5File;

        public MD5Callback(File file) {
            md5File = file;
        }

        public File getMd5File() {
            return md5File;
        }

        public abstract void onResult(File file, String md5Value);

        public abstract void onError(Exception e);
    }

    private static class MD5Worker extends AsyncExecutor.Worker<String> {

        private MD5Callback md5Callback;

        public MD5Worker(MD5Callback callback) {
            this.md5Callback = callback;
        }

        @Override
        protected String doInBackground() {

            try {
                return MD5UtilForAndroid.getMD5(md5Callback.getMd5File());
            } catch (Exception e) {
                md5Callback.onError(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            md5Callback.onResult(md5Callback.getMd5File(),data);
        }

        @Override
        protected void onCanceled() {
            super.onCanceled();
            md5Callback.onError(new RuntimeException("calculate md5 value process be canceled"));
        }

        @Override
        protected void abort() {
            super.abort();
            md5Callback.onError(new RuntimeException("calculate md5 value process be abort"));
        }
    }



    /* 对文件全文生成MD5摘要
    *
    * @param file 要加密的文件
    * @return MD5摘要码
    */
    public static String getMD5(File file) {
        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[2048];
            int length = -1;
            long s = System.currentTimeMillis();

            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            byte[] b = md.digest();
            return byteToHexString(b);
            // 16位加密
            // return buf.toString().substring(8, 24);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 把byte[]数组转换成十六进制字符串表示形式
     * @param tmp    要转换的byte[]
     * @return 十六进制字符串表示形式
     */
    private static String byteToHexString(byte[] tmp) {
        String s;
        BigInteger bi = new BigInteger(1, tmp);
        s = bi.toString(16);
        return s;
    }
}
