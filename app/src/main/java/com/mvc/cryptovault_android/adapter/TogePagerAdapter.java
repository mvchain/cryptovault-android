package com.mvc.cryptovault_android.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseActivity;

import java.util.ArrayList;

public class TogePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    private FragmentManager fm;
    private int[] titles = {R.string.toge_comming_soon, R.string.toge_get_on, R.string.toge_over, R.string.toge_involved};
    private Context context;

    public TogePagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, Context context) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
        this.context = context;
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
        return context.getString(titles[position]);
    }
}
