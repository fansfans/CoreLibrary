package com.core.corelib.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public abstract class GlobalBaseFragment2 extends Fragment implements HttpCycleContext, RxAction{


    protected TitleBar mTitleBar;

    private Subscription mSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus3Helper.register(this);
        mSubscription = RxBus.getInstance().subscribe(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lib_fragment_base_global, null);
        ViewStub viewStub = (ViewStub)view.findViewById(R.id.view_stub);
        if (getLayoutResId() != 0) {
            viewStub.setLayoutResource(getLayoutResId());
            viewStub.inflate();
        }
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            mTitleBar = (TitleBar)view.findViewById(R.id.title_bar);
            initTitleBarCommon(mTitleBar);
            if (!initTitleBar(mTitleBar)) {
                mTitleBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        LeakCanaryHelper.watch(this);
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
    public void call(Object o) {
        if (o instanceof RxBusEvent) {
            onRxBusEvent((RxBusEvent)o);
        }
    }

    protected void onRxBusEvent(RxBusEvent event) {

    }

    private final void initTitleBarCommon(TitleBar titleBar) {
        titleBar.setBackgroundColor(getResources().getColor(R.color.titlebar_bg2));
        titleBar.setDividerColor(getResources().getColor(R.color.titlebar_divider_line_bg2));
        titleBar.setLeftTextColor(getResources().getColor(R.color.titlebar_text_color2));
        titleBar.setTitleColor(getResources().getColor(R.color.titlebar_text_color2));
        titleBar.setActionTextColor(getResources().getColor(R.color.titlebar_text_color2));
    }

    protected void setBack() {
        if (mTitleBar != null) {
            mTitleBar.setLeftImageResource(R.drawable.ic_app_back);
            mTitleBar.setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
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
                    getActivity().finish();
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

    protected abstract void initData(Bundle savedInstanceState);



}
