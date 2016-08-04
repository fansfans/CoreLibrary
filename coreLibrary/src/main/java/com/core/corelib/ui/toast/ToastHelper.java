package com.core.corelib.ui.toast;

import android.app.Activity;
import android.content.Context;

import com.github.johnpersano.supertoasts.SuperActivityToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.OnClickWrapper;

/**
 * Created by admin on 16/2/3.
 */
public class ToastHelper {



    public static void show(Context context, CharSequence message) {

        SuperToast.create(context, message, SuperToast.Duration.SHORT).show();
    }

    public static void show(Context context,  CharSequence message, int duration) {

        SuperToast.create(context, message, duration).show();
    }

    private static void show(Context context,  CharSequence message, int duration,int backgroundColor, int textColor) {

        SuperToast superToast = new SuperToast(context);
        superToast.setText(message);
        superToast.setDuration(duration);
        superToast.setBackground(backgroundColor);
        superToast.setTextColor(textColor);
        superToast.show();
    }

    public static void showButton(Activity activity, CharSequence message, CharSequence undoMessage) {

        SuperActivityToast superActivityToast = new SuperActivityToast(activity, SuperToast.Type.BUTTON);
        superActivityToast.setDuration(SuperToast.Duration.EXTRA_LONG);
        superActivityToast.setText(message);
        superActivityToast.setButtonIcon(SuperToast.Icon.Dark.UNDO, undoMessage);
        superActivityToast.setOnClickWrapper(null);
        superActivityToast.show();
    }

    public static void showButton(Activity activity, CharSequence message, CharSequence undoMessage, OnClickWrapper onClickWrapper) {

        SuperActivityToast superActivityToast = new SuperActivityToast(activity, SuperToast.Type.BUTTON);
        superActivityToast.setDuration(SuperToast.Duration.EXTRA_LONG);
        superActivityToast.setText(message);
        superActivityToast.setButtonIcon(SuperToast.Icon.Dark.UNDO, undoMessage);
        superActivityToast.setOnClickWrapper(onClickWrapper);
        superActivityToast.show();
    }



    public static class Duration extends SuperToast.Duration {

        public static final int VERY_SHORT = 1500;
        public static final int SHORT = 2000;
        public static final int MEDIUM = 2750;
        public static final int LONG = 3500;
        public static final int EXTRA_LONG = 4500;

        private Duration(){}

    }


}
