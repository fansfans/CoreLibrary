package com.core.corelib.ui.recycler.listener;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by admin on 16/2/23.
 */
public interface OnRefreshListener extends SwipeRefreshLayout.OnRefreshListener {

    @Override
    void onRefresh();
}
