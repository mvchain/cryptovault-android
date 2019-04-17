package com.mvc.ttpay_android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mvc.ttpay_android.MyApplication;
import com.mvc.ttpay_android.R;

import java.util.ArrayList;

public class OptionPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    private FragmentManager fm;
    private String[] titles = {MyApplication.getAppContext().getString(R.string.interested), MyApplication.getAppContext().getString(R.string.pending),MyApplication.getAppContext().getString(R.string.extracted)};

    public OptionPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
