package com.lzl_rjkx.doctor.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MyHeadPagerAdapter extends PagerAdapter {

    private List<ImageView> imageViews = new ArrayList<>();

    public MyHeadPagerAdapter(List<ImageView> imageViews) {
        this.imageViews = imageViews;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public void startUpdate(View arg0) {
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        if (position == imageViews.size()) {
            return;
        }
        ((ViewPager) container).removeView(imageViews.get(position));
    }

    @Override
    public Object instantiateItem(final View container, final int position) {
        ((ViewPager) container).addView(imageViews.get(position));
        ImageView iv = imageViews.get(position);
        return iv;
    }
}
