package com.core.corelib.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.core.corelib.R;
import com.core.corelib.eventbus3.EventBus3Helper;
import com.core.corelib.eventbus3.event.BaseEvent;
import com.core.corelib.leak.LeakCanaryHelper;
import com.core.network.NetworkHelper;
import com.core.network.cyclecontext.HttpCycleContext;
import com.core.corelib.ui.widget.TitleBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by admin on 15/12/16.
 */
public abstract class GlobalBaseFragment extends Fragment implements HttpCycleContext{


    protected TitleBar mTitleBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus3Helper.register(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {

            if (getTitleBarId() != -1) {
                mTitleBar = (TitleBar)view.findViewById(getTitleBarId());
                initTitleBarCommon(mTitleBar);
                initTitleBar(mTitleBar);
            }
            initView(view);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        LeakCanaryHelper.watch(this);
        EventBus3Helper.unregister(this);

        NetworkHelper.cancel(this);
    }

    @Override
    public Object getHttpTaskTag() {
        return "HTTP_TASK_TAG" + hashCode();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {

    }

    protected int getTitleBarId() {
        return -1;
    }

    private final void initTitleBarCommon(TitleBar titleBar) {
        titleBar.setBackgroundColor(getResources().getColor(R.color.titlebar_bg));
        titleBar.setDividerColor(getResources().getColor(R.color.titlebar_divider_line_bg));
        titleBar.setLeftTextColor(getResources().getColor(R.color.titlebar_text_color));
        titleBar.setTitleColor(getResources().getColor(R.color.titlebar_text_color));
        titleBar.setActionTextColor(getResources().getColor(R.color.titlebar_text_color));
    }

    protected void initTitleBar(TitleBar titleBar) {

    }

    protected abstract void initView(View view);


    protected abstract void initData(Bundle savedInstanceState);



}
