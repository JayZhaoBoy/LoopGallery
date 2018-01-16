package com.example.loop_gallery_vp;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jayzhao on 2018/1/4.
 */

public class TestPagerAdapter extends PagerAdapter implements WrapperAdapterListener {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getRightPosition() {
        return 0;
    }

    @Override
    public View getRightView(ViewGroup container, int position) {
        return (View)instantiateItem(container, position);
    }
}
