package com.example.jayzhao.loopgallery;

import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.loop_gallery_vp.LoopGalleryByViewPager;
import com.example.loop_gallery_vp.WrapperAdapterListener;

public class MainActivity extends AppCompatActivity {
    private LoopGalleryByViewPager mLoopGalleryByViewPager;
    private int[] imgIds = {R.mipmap.cat1, R.mipmap.cat2, R.mipmap.cat3, R.mipmap.cat4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        mLoopGalleryByViewPager = (LoopGalleryByViewPager) findViewById(R.id.viewPager);
        mLoopGalleryByViewPager.setAdapter(new MyPagerAdapter());
    }

    class MyPagerAdapter extends PagerAdapter implements WrapperAdapterListener {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_content, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.img);
            Button button = (Button) view.findViewById(R.id.button);

            imageView.setImageResource(imgIds[position]);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getRightCount() {
            return imgIds.length;
        }

        @Override
        public View getRightView(ViewGroup viewGroup, int position) {
            return (View) instantiateItem(viewGroup, position);
        }
    }
}
