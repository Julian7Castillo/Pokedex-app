package com.example.proyecto_pokemon.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.proyecto_pokemon.R;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;

    int images[] = {
            R.drawable.image2,
            R.drawable.image1,
            R.drawable.image4,
            R.drawable.image3
    };

    int headings[]={
            R.string.heading_one,
            R.string.heading_two,
            R.string.heading_three,
            R.string.heading_fourth,
    };

    int description[]={
            R.string.desc_one,
            R.string.desc_two,
            R.string.desc_three,
            R.string.desc_fourth
    };

    public ViewPagerAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.card_slider_layout, container, false);

        ImageView imgTitule = (ImageView) view.findViewById(R.id.imgTitule);
        TextView txTitulo = (TextView) view.findViewById(R.id.txTitulo);
        TextView txDescription = (TextView) view.findViewById(R.id.txDescription);

        imgTitule.setImageResource(images[position]);
        txTitulo.setText(headings[position]);
        txDescription.setText(description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
