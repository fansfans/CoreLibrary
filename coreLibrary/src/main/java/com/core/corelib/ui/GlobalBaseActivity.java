package com.core.corelib.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import butterknife.ButterKnife;

/**
 * Created by admin on 15/12/16.
 */
public abstract class GlobalBaseActivity extends AppCompatActivity implements HttpCycleContext{


    protected TitleBar mTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getLayoutResId() != 0) {
            setContentView(getLayoutResId());
            ButterKnife.bind(this);
            if (getTitleBarId() != -1) {
                mTitleBar = (TitleBar)findViewById(getTitleBarId());
                if (mTitleBar != null) {
                    initTitleBarCommon(mTitleBar);
                    initTitleBar(mTitleBar);
                }
            }
            initView();
            initData();
        }
        EventBus3Helper.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LeakCanaryHelper.watch(this);

        ButterKnife.unbind(this);
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

    protected abstract int getLayoutResId();

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

    protected void setBack() {
        if (mTitleBar != null) {
            mTitleBar.setLeftImageResource(R.drawable.ic_app_back);
            mTitleBar.setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected void setBack(View.OnClickListener listener) {
        if (mTitleBar != null) {
            mTitleBar.setLeftImageResource(R.drawable.ic_app_back);
            mTitleBar.setLeftClickListener(listener);
        }
    }

    protected void setBackCancel() {
        if (mTitleBar != null) {
            mTitleBar.setLeftText(getString(R.string.common_text_cancel));
            mTitleBar.setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected void setBackCancel(View.OnClickListener listener) {
        if (mTitleBar != null) {
            mTitleBar.setLeftText(getString(R.string.common_text_cancel));
            mTitleBar.setLeftClickListener(listener);
        }
    }

    protected void setBackText(CharSequence charSequence, View.OnClickListener listener) {

        if (mTitleBar != null) {
            mTitleBar.setLeftText(charSequence);
            mTitleBar.setLeftClickListener(listener);
        }
    }

    protected abstract void initView();

    protected abstract void initData();

}
