package com.santosh.android.class_app;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder>{

  private List<list_item2> list_items;
  private Context context;
  private list_item2 temp;


    public MyAdapter2(List<list_item2> list_items, Context context) {
      this.list_items = list_items;
      this.context = context;
  }

  @Override
  @NonNull
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

      View v = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.list_item2, parent,false);
      return new ViewHolder(v);
  }
  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
      final list_item2 listItem = list_items.get(position);
      temp = listItem;
      holder.date.setText(listItem.getDate2());
      holder.source.setText("source : "+listItem.getSource());
      holder.title.setText(listItem.getTitle2());
      holder.desc.setText(listItem.getDescription2());
      Picasso.with(context).load(listItem.getImg()).into(holder.imageView2);
      holder.cl2.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View view) {
              downloadImage(listItem.getImg(),listItem.getTitle2()+".jpg");
              return false;
          }
      });
  }
    DownloadManager downloadManager;
    private void downloadImage(String img, String nam) {
        downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
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

    @Override
  public int getItemCount() {
      return list_items.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder{

      public TextView date;
      public TextView title;
      public TextView desc;
      public TextView source;
      public ImageView imageView2;
      public android.support.constraint.ConstraintLayout cl2;
      public ViewHolder(View itemView) {
          super(itemView);
          date = (TextView) itemView.findViewById(R.id.tv2_date);
          source = (TextView) itemView.findViewById(R.id.tv2_source);
          title = (TextView) itemView.findViewById(R.id.tv2_title);
          desc = (TextView) itemView.findViewById(R.id.tv2_des);
          imageView2= (ImageView) itemView.findViewById(R.id.iv2_img);
          cl2 = (android.support.constraint.ConstraintLayout) itemView.findViewById(R.id.constraintlayout2);

      }
  }
}