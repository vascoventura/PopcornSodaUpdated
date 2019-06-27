package com.example.popcornsoda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.popcornsoda.R;

import java.util.List;

public class SliderPageAdapter extends PagerAdapter{

    private Context mContext;
    private List<Slider> mList;

    public SliderPageAdapter(Context mContext, List<Slider> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slider_item, null);

        ImageView slideImage = slideLayout.findViewById(R.id.slider_img);
        TextView slideText = slideLayout.findViewById(R.id.slider_titulo);
        TextView slideText2 = slideLayout.findViewById(R.id.slider_descricao);

        slideImage.setImageResource(mList.get(position).getImage());
        slideText.setText(mList.get(position).getTitle());
        slideText2.setText(mList.get(position).getDescricao());

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
