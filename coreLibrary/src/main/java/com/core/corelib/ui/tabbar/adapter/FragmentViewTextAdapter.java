package com.core.corelib.ui.tabbar.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.core.corelib.ui.tabbar.TabBar;

/**
 * Created by admin on 16/3/14.
 */
public abstract class FragmentViewTextAdapter extends FragmentStatePagerAdapter implements TabBar.ViewTextTabProvider {

    public FragmentViewTextAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public final Fragment getItem(int position) {
        return getTabFragment(position);
    }

    @Override
    public final int getCount() {
        return getTabCount();
    }

    @Override
    public final CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
