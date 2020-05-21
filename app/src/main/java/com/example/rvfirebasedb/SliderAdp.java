package com.example.rvfirebasedb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import android.support.v4.view.ViewPager;
public class SliderAdp extends PagerAdapter
{
    Context mContext;
    Integer [] images;

    public SliderAdp(Context mContext, Integer[] images) {
        this.mContext = mContext;
        this.images = images;
    }
    @Override
    public int getCount() {
        return images.length;
    }

    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView=new ImageView(mContext);

        Picasso.with(mContext).load(images[position])
                .fit()
                .centerCrop()
                .into(imageView);

        container.addView(imageView);

        return imageView;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}

