package com.example.loop_gallery_vp;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.HashMap;

/**
 * Created by jayzhao on 2018/1/4.
 */

public interface WrapperAdapterListener{

    /**
     * get right position
     * @return
     */
    int getRightCount();

    /**
     * get right View
     * @return
     */
    View getRightView(ViewGroup viewGroup, int position);

//    HashMap<Integer, View>
}
