package com.core.corelib.leak;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by admin on 16/1/17.
 */
public class LeakCanaryHelper {

    private static RefWatcher mRefWatcher ;

    public static void init(Application application, boolean debug) {

        if (!debug) {
            mRefWatcher = RefWatcher.DISABLED;
            return;
        }
        mRefWatcher = LeakCanary.install(application);
    }

    public static void watch(Object watchedReference) {
        mRefWatcher.watch(watchedReference);
    }

    public static void watch(Object watchedReference, String referenceName) {
        mRefWatcher.watch(watchedReference, referenceName);
    }
}
