package com.core.corelib.ui.widget;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.core.corelib.R;
import com.core.corelib.ui.loading.LoadingRing;

import java.util.Random;

/**
 * Created by admin on 15/12/18.
 */
public class LoadingDialog extends AppCompatDialogFragment {

    private static final int RANDOM_STRING_LENGTH = 10;

    private static LoadingDialog instance;

    private LoadingRing loading;

    public LoadingDialog() {}


    public static LoadingDialog getInstance() {
//        if (instance == null) {
//            synchronized (LoadingDialog.class) {
//                if (instance == null) {
//                    instance = new LoadingDialog();
//                }
//            }
//        }
        return new LoadingDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.lib_loading_dialog, null);
        init(contentView);

        AppCompatDialog dialog = new AppCompatDialog(getContext());
        dialog.supportRequestWindowFeature(STYLE_NO_TITLE);
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private void init(View view) {

        loading = (LoadingRing) view.findViewById(R.id.loading);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        loading.startAnim();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        loading.stopAnim();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean isShowing() {
        return isVisible();
    }

    public void show(FragmentManager manager) {
        String tag = getRandomString(RANDOM_STRING_LENGTH);
        show(manager, tag);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        show(manager.beginTransaction(), tag);
//        super.show(manager, tag);
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {

        transaction.add(this, tag);
        return transaction.commitAllowingStateLoss();
//        return super.show(transaction, tag);
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }

    @Override
    public void dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss();
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
    }

    @Override
    public boolean isCancelable() {
        return super.isCancelable();
    }

    private static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


}
