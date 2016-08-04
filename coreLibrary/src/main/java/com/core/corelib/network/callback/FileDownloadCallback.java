package com.core.corelib.network.callback;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;

/**
 * Created by admin on 16/1/19.
 */
public abstract class FileDownloadCallback extends FileCallBack {


    private File mDestFile;

    public FileDownloadCallback(File destFile) {
        super(destFile.getParent(), destFile.getName());
        this.mDestFile = destFile;
    }

    public abstract void onSuccess(File destFile);

    public abstract void onFailure(okhttp3.Request request, Exception e, File destFile);

    public abstract void inProgress(float progress, long current, long total);

//    @Override
//    public void inProgress(float progress) {
//
//    }
//
//    @Override
//    public void onError(Call call, Exception e) {
//        onFailure(call.request(), e, mDestFile);
//
//    }
//
//    @Override
//    public void onResponse(File response) {
//        onSuccess(response);
//    }

    @Override
    public void inProgress(float progress, long total, int id) {
        super.inProgress(progress, total, id);
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        onFailure(call.request(), e, mDestFile);
    }

    @Override
    public void onResponse(File response, int id) {
        onSuccess(response);
    }
//
//    @Override
//    public File saveFile(Response response, int id) throws IOException {
//        return super.saveFile(response, id);
//    }

    @Override
    public File saveFile(okhttp3.Response response, int id) throws IOException {

        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try
        {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;

            L.e(total + "");

            File file = mDestFile;
//            File dir = new File(destFileDir);
//            if (!dir.exists())
//            {
//                dir.mkdirs();
//            }
//            File file = new File(dir, destFileName);
//            fos = new FileOutputStream(file);
            if (!file.exists()) {
                File parent = file.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1)
            {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                OkHttpUtils.getInstance().getDelivery().execute(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        inProgress(finalSum * 1.0f / total, finalSum, total);
                    }
                });
            }
            fos.flush();

            return file;

        } finally
        {
            try
            {
                if (is != null) is.close();
            } catch (IOException e)
            {
            }
            try
            {
                if (fos != null) fos.close();
            } catch (IOException e)
            {
            }

        }
    }

}
