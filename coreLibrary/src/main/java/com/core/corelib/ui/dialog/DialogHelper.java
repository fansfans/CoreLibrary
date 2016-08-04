package com.core.corelib.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;

import com.flyco.dialog.entity.DialogMenuItem;

import java.util.ArrayList;

/**
 * Created by admin on 16/2/2.
 */
public class DialogHelper {


    /**
     *  平常的对话框，确定 取消那种
     */
    public static class NormalDialog extends com.flyco.dialog.widget.NormalDialog {

        public NormalDialog(Context context) {
            super(context);
            style(STYLE_TWO);
        }



    }

    /**
     *  一列可选择的
     */
    public static class NormalListDialog extends com.flyco.dialog.widget.NormalListDialog {

        public NormalListDialog(Context context, ArrayList<DialogMenuItem> baseItems) {
            super(context, baseItems);
        }

        public NormalListDialog(Context context, String[] items) {
            super(context, items);
        }

        public NormalListDialog(Context context, BaseAdapter adapter) {
            super(context, adapter);
        }
    }

    /**
     *  谷歌设计风格的
     */
    public static class MaterialDialog extends com.flyco.dialog.widget.MaterialDialog {

        public MaterialDialog(Context context) {
            super(context);
        }
    }

    /**
     *  类似于苹果手机底部弹出
     */
    public static class ActionSheetDialog extends com.flyco.dialog.widget.ActionSheetDialog {


        public ActionSheetDialog(Context context, ArrayList<DialogMenuItem> baseItems, View animateView) {
            super(context, baseItems, animateView);
        }

        public ActionSheetDialog(Context context, String[] items, View animateView) {
            super(context, items, animateView);
        }

        public ActionSheetDialog(Context context, BaseAdapter adapter, View animateView) {
            super(context, adapter, animateView);
        }

    }
}
