package com.example.proyecto_pokemon.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.proyecto_pokemon.Adapters.ViewPagerAdapter;
import com.example.proyecto_pokemon.R;

public class IntroductionActivity extends AppCompatActivity {

    private Button btnSkip, btnBack, btnNext;
    TextView[] dots;
    ViewPager SlideViewPager;
    LinearLayout DotLayout;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        btnSkip = findViewById(R.id.btnSkip);
        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroductionActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getitem(0) > 0){
                    SlideViewPager.setCurrentItem(getitem(-1),true);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getitem(0)< 3){
                    SlideViewPager.setCurrentItem(getitem(1),true);
                }else {
                    Intent intent = new Intent(IntroductionActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        SlideViewPager = (ViewPager) findViewById(R.id.slideView);
        DotLayout = (LinearLayout) findViewById(R.id.inicator);
        viewPagerAdapter = new ViewPagerAdapter(this);
        SlideViewPager.setAdapter(viewPagerAdapter);

        setUpIndicator(0);
        SlideViewPager.addOnPageChangeListener(viewListener);
    }

    public void setUpIndicator(int position){
        dots = new TextView[4];
        DotLayout.removeAllViews();

        for(int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive, getApplicationContext().getTheme()));
            DotLayout.addView(dots[i]);
        }

        dots[position].setTextColor(getResources().getColor(R.color.red, getApplicationContext().getTheme()));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setUpIndicator(position);

            if(position > 0){
                btnBack.setVisibility(View.VISIBLE);
            }else{
                btnBack.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getitem (int i){
        return SlideViewPager.getCurrentItem()+i;
    }
}