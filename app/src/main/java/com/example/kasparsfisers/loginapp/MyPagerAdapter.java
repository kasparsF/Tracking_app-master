package com.example.kasparsfisers.loginapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by kaspars.fisers on 11/2/2016.
 */

public  class MyPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;


    public MyPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FirstFragment();
        } else if (position == 1) {
            return new FirstFragment();
        } else {
            return new FirstFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


}
