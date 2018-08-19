package com.santosh.android.class_app;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;


public class TimeActivity extends AppCompatActivity {

    String url = "https://thecodont-tears.000webhostapp.com/files_of_app/tt.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        setTitle("Time table");
        PhotoView photoView = findViewById(R.id.photo_view);
        photoView.setImageResource(R.mipmap.loadingbar);
        //Picasso.with(this).load(url).into(photoView);
        Picasso.with(this).load(url).placeholder(R.mipmap.loadingbar).into(photoView);
        //photoView.setImageResource(R.mipmap.tt);
        //GlobalClass.deleteCache(getApplicationContext());

        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    downloadImage(url,"tt.jpg");
                }
                catch(Exception e){

                }
            }
        });
    }
    DownloadManager downloadManager;
    private void downloadImage(String img, String nam) {
        downloadManager = (DownloadManager)getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(img));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(nam)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nam);
        downloadManager.enqueue(request);
    }
}
