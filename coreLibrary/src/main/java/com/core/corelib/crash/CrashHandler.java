package com.core.corelib.crash;

/**
 * Created by admin on 16/7/13.
 */
public abstract class CrashHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        ex.printStackTrace();

        launchApp();
        clearData();

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    protected abstract void launchApp();

    protected abstract void clearData();

}
