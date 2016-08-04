package com.core.corelib.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;

import com.core.corelib.R;
import com.core.corelib.eventbus3.EventBus3Helper;
import com.core.corelib.eventbus3.event.BaseEvent;
import com.core.corelib.leak.LeakCanaryHelper;
import com.core.network.NetworkHelper;
import com.core.network.cyclecontext.HttpCycleContext;
import com.core.corelib.rxbus.event.RxBusEvent;
import com.core.corelib.rxbus.RxAction;
import com.core.corelib.rxbus.RxBus;
import com.core.corelib.ui.widget.TitleBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by admin on 15/12/16.
 */
public abstract class GlobalBaseActivity2 extends AppCompatActivity implements HttpCycleContext, RxAction{


    protected TitleBar mTitleBar;

    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lib_activity_base_global);

        ViewStub viewStub = (ViewStub)findViewById(R.id.view_stub);
        if (getLayoutResId() != 0) {
            viewStub.setLayoutResource(getLayoutResId());
            viewStub.inflate();
        }

        ButterKnife.bind(this);
        mTitleBar = (TitleBar)findViewById(R.id.title_bar);
        initTitleBarCommon(mTitleBar);
        if (!initTitleBar(mTitleBar)) {
            mTitleBar.setVisibility(View.GONE);
        }
        initData();
        EventBus3Helper.register(this);
        mSubscription = RxBus.getInstance().subscribe(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LeakCanaryHelper.watch(this);

        ButterKnife.unbind(this);
        EventBus3Helper.unregister(this);
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }

        NetworkHelper.cancel(this);
    }

    @Override
    public Object getHttpTaskTag() {
        return "HTTP_TASK_TAG" + hashCode();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {

    }

    @Override
    public final void call(Object o) {
        if (o instanceof RxBusEvent) {
            onRxBusEvent((RxBusEvent)o);
        }
    }


    protected void onRxBusEvent(RxBusEvent event) {

    }

    private void initTitleBarCommon(TitleBar titleBar) {
        titleBar.setBackgroundColor(getResources().getColor(R.color.titlebar_bg2));
        titleBar.setDividerColor(getResources().getColor(R.color.titlebar_divider_line_bg2));
        titleBar.setLeftTextColor(getResources().getColor(R.color.titlebar_text_color));
        titleBar.setTitleColor(getResources().getColor(R.color.titlebar_text_color));
        titleBar.setActionTextColor(getResources().getColor(R.color.titlebar_text_color));
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


    protected abstract int getLayoutResId();

    protected abstract boolean initTitleBar(TitleBar titleBar);

    protected abstract void initData();

}
