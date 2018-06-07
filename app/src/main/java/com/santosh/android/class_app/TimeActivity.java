package com.santosh.android.class_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;


public class TimeActivity extends AppCompatActivity {

    String url = "http://santosh36.ga/login_image.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        setTitle("Time table");
        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
       // photoView.setImageResource(R.mipmap.temp_tt);
        Picasso.with(this).load(url).into(photoView);
        Picasso.with(this).load(url).placeholder(R.mipmap.tt_placeholder).into(photoView);

        GlobalClass.deleteCache(getApplicationContext());
    }
}
