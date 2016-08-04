package com.core.corelib.crash;

import android.util.Log;

import com.core.common.io.IOUtils;
import com.core.common.utils.FileUtil;
import com.core.corelib.json.JsonHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 16/7/13.
 */
public class CrashModel {

    public static final String TAG = CrashModel.class.getSimpleName();

//    private static final String KEY_CRASH_TIMES = "crash_times";
    private static final String CRASH_TIME_FILE_NAME = "crash_time";
    //不能通过App.getPackageName来获取包名，否则会有问题，只能默认为cn.campusapp.campus。所以对于debug或者运营版本，清数据会把release的清掉
//    private static final String FILE_DIR = String.format("/data/data/%s/", BuildConfig.APPLICATION_ID);
//    private static final String ACCOUNT_FILE_NAME = String.format("%s%s", FILE_DIR, "shared_prefs/account_pref.xml");

//    private static ArrayList<String> FILES_DONT_NEED_DELETE = new ArrayList<>();  //该目录中的文件不会被删除

//    private List<File> FILES_NEED_DELETE = null;
    private List<File> FILES_DONT_DELETE = null;

//    static {
//        FILES_DONT_NEED_DELETE.add(ACCOUNT_FILE_NAME);  //目前账号信息文件不会被删除，但是会手动改变数据，只保留userId  accessToken 和school
//    }

    protected ArrayList<Long> mCrashTimes;
//    Gson gson = new Gson();
    private File mFileDir;

    private File mCacheFileDir;
    private File mSharePreferenceFileDir;

    public CrashModel(String APPLICATION_ID) {

        mFileDir = new File(String.format("/data/data/%s/", APPLICATION_ID));
        mCacheFileDir = new File(String.format("/data/data/%s/%s/", APPLICATION_ID, "cache"));
        mSharePreferenceFileDir = new File(String.format("/data/data/%s/%s/", APPLICATION_ID, "shared_prefs"));

        FILES_DONT_DELETE = new ArrayList<>();
//        FILES_DONT_DELETE.add(mCacheFileDir);
        FILES_DONT_DELETE.add(mSharePreferenceFileDir);
        init();
    }

    private void init() {

        mCrashTimes = readCrashTimes();
        if (mCrashTimes == null) {
            mCrashTimes = new ArrayList<>();
            storeCrashTimes(mCrashTimes);
        }
    }


    public void checkAndClearData() {
        long timeNow = System.currentTimeMillis();

        if (checkClearData(timeNow, new ArrayList<>(mCrashTimes))) {
            Log.i(TAG, "已经在5分钟之内有三次闪退，需要清理数据");
            try {
                clearData();
            } catch (Exception e) {
                Log.e(TAG, "清空所有数据失败");
            }
        } else {
            mCrashTimes.add(timeNow);
            storeCrashTimes(mCrashTimes);
            Log.i(TAG, String.format("此次不需要清空数据, %s", JsonHelper.serialize(mCrashTimes)));
        }
    }

    private void storeCrashTimes(ArrayList<Long> crashTimes) {
        try {
            String str = JsonHelper.serialize(crashTimes);
            IOUtils.write(str, new FileOutputStream(new File(mFileDir, CRASH_TIME_FILE_NAME)));
//            Files.writeToFile(mFileDir, CRASH_TIME_FILE_NAME, str);
        } catch (Exception e) {
            Log.e(TAG, "保存闪退时间失败");
        }

    }

    private ArrayList<Long> readCrashTimes() {
        try {
//            String timeStr = IOUtils.read(new FileInputStream(new File(mFileDir, CRASH_TIME_FILE_NAME)), new byte[4]);
//                    Files.readFileContent(mFileDir, CRASH_TIME_FILE_NAME);
//            return JsonHelper.deserializeList(timeStr, TypeUtils.getClass(Long.class));
//            StringBuilder builder = new StringBuilder();
//            byte[] buffer = new byte[4];
//            while (IOUtils.read(new FileInputStream(new File(mFileDir, CRASH_TIME_FILE_NAME)), new byte[4]) != -1) {
//                builder.append(buffer.toString());
//            }
//            return JSON.parseArray(timeStr, new TypeReference<ArrayList<Long>>().getType());
            FileInputStream in = new FileInputStream(new File(mFileDir, CRASH_TIME_FILE_NAME));
            int size = in.available();
            byte[] bytes = new byte[size];

            IOUtils.read(in, bytes);

            return (ArrayList<Long>) JsonHelper.deserializeList(new String(bytes), Long.class);
        } catch (Exception e) {
            Log.e(TAG, "读取闪退时间失败");
        }
        return null;
    }

    /**
     * 检查是否需要清空数据，目前的清空策略是在5分钟之内有三次闪退的就清空数据，也就是从后往前遍历，只要前两次闪退发生在5分钟之内，就清空数据
     *
     * @return
     */
    private boolean checkClearData(long time, ArrayList<Long> crashTimes) {
        Log.i(TAG, JsonHelper.serialize(crashTimes));
        int count = 0;
        for (int i = crashTimes.size() - 1; i >= 0; i--) {
            long crashTime = crashTimes.get(i);
            if (time - crashTime <= 5 * 60 * 1000) {
                count++;
                if (count >= 2) {
                    break;
                }
            }
        }
        if (count >= 2) {
            //在5分钟之内有三次闪退，这时候需要清空数据
            return true;
        } else {
            return false;
        }
    }

    /**
     * 清空数据，包括数据库中的和SharedPreferences中的
     *
     * @throws Exception
     */
    private void clearData() throws Exception {
        Log.i(TAG, "开始清理数据");
        FileUtil.deleteFilesExceptSomeInDirectory(mFileDir, FILES_DONT_DELETE);
    }
}
