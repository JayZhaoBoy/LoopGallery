package com.example.loop_gallery_vp;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
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
    private int mDefaultPageIndex = 1;
    private ViewPager mViewPager;
    private OnItemClickListener mItemClickListener;

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
        setClipChildren(false);
        if (attrs == null) {
            return;
        }
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoopGalleryByViewPager);
        isLoop = array.getBoolean(R.styleable.LoopGalleryByViewPager_isLoop, true);
        mViewPager = new ViewPager(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        this.addView(mViewPager, params);
    }

    public void setAdapter(PagerAdapter aAdapter) {
        if (!(aAdapter instanceof WrapperAdapterListener)) {
            throw new IllegalArgumentException("adapter must implement WrapperAdapterListener");
        }
        LoopGalleryByViewPagerAdapter loopGalleryByViewPagerAdapter = new LoopGalleryByViewPagerAdapter();
        loopGalleryByViewPagerAdapter.setAdapter(aAdapter);
        mViewPager.setAdapter(loopGalleryByViewPagerAdapter);
        if (mDefaultPageIndex == 0 && isLoop){  //循环的情况下默认选中第二页，因为第一页是最后一项
            mDefaultPageIndex = 1;
        }
        mViewPager.setCurrentItem(mDefaultPageIndex, false);
    }

    /**
     * 添加页面滑动监听
     * @param listener
     */
    public void addOnPageChangeListener(final ViewPager.OnPageChangeListener listener) {
        if (listener == null || mViewPager == null) {
            return;
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int newPosition = getRightPosition(position);
                listener.onPageScrolled(newPosition, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                int newPosition = getRightPosition(position);
                listener.onPageSelected(newPosition);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                listener.onPageScrollStateChanged(state);
            }
        });

    }


    /**
     * 计算返回可用的position
     * @return
     */
    private int getRightPosition(int originPosition) {
        if (!isLoop || originPosition < 0) {
            return originPosition;
        }
        int count = mViewPager.getChildCount();
        int newPosition = 0;
        if (originPosition == 0) {
            newPosition = count - 3;
        } else if (originPosition == count - 1) {
            newPosition = 0;
        } else {
            newPosition = originPosition - 1;
        }
        return newPosition;
    }


    /**
     * 包装adapter
     */
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
            int count = getCount();
            View newView = null;
            if (!(adapter instanceof WrapperAdapterListener)) {
                return newView;
            }
            if (isLoop) {
                if (position == 0) {
                    newView = ((WrapperAdapterListener) adapter).getRightView(container, count - 3);
                } else if (position == count - 1) {
                    newView = ((WrapperAdapterListener) adapter).getRightView(container, 0);
                } else {
                    newView = ((WrapperAdapterListener) adapter).getRightView(container, position - 1);
                }
            } else {
                newView = ((WrapperAdapterListener) adapter).getRightView(container, position);
            }
            newView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.click(view, position);
                    }
                }
            });
            container.addView(newView);
            return newView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public int getDefaultPageIndex() {
        return mDefaultPageIndex;
    }

    public void setDefaultPageIndex(int defaultPageIndex) {
        mDefaultPageIndex = defaultPageIndex;
        if (mViewPager != null) {
            mViewPager.setCurrentItem(mDefaultPageIndex, false);
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    /**
     * 点击事件传递接口
     */
    public interface OnItemClickListener {
        void click(View view, int position);
    }

}
