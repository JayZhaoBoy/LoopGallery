package com.example.loop_gallery_vp;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

/**
 * Created by jayzhao on 2018/1/3.
 */

public class LoopGalleryByViewPager extends FrameLayout {
    //是否支持循环，默认为true
    private boolean isLoop = true;

    public LoopGalleryByViewPager(@NonNull Context context) {
        super(context);
    }

    public LoopGalleryByViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public LoopGalleryByViewPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs == null){
            return;
        }
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoopGalleryByViewPager);
        isLoop = array.getBoolean(R.styleable.LoopGalleryByViewPager_isLoop, true);
    }

    private void setAdapter(PagerAdapter aAdapter) {
        if (!(aAdapter instanceof WrapperAdapterListener)) {
            throw new IllegalArgumentException("adapter must implement WrapperAdapterListener");
        }
        LoopGalleryByViewPagerAdapter loopGalleryByViewPagerAdapter = new LoopGalleryByViewPagerAdapter();
        loopGalleryByViewPagerAdapter.setAdapter(aAdapter);
    }

    class LoopGalleryByViewPagerAdapter extends PagerAdapter {
        private PagerAdapter adapter;

        public void setAdapter(PagerAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getCount() {
            return adapter instanceof WrapperAdapterListener ?
                    (isLoop ? ((WrapperAdapterListener) adapter).getRightCount() + 2 : ((WrapperAdapterListener) adapter).getRightCount())
                    : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View newView = null;
            if (!(adapter instanceof WrapperAdapterListener)){
                return newView;
            }
            if (isLoop){
                if (position == 0){
                    newView = ((WrapperAdapterListener) adapter).getRightView(container, getCount() - 1);
                }else if (position == getCount() - 1){
                    newView = ((WrapperAdapterListener) adapter).getRightView(container, 0);
                }else {
                    newView = ((WrapperAdapterListener) adapter).getRightView(container, position);
                }
            }else {
                newView = ((WrapperAdapterListener) adapter).getRightView(container, position);
            }
            newView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null){mItemClickListener.click(view, position);}
                }
            });
            return newView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }

    private OnItemClickListener mItemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void click(View view, int position);
    }

}
