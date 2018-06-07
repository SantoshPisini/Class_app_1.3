package com.santosh.android.class_app;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
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

  public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.ViewHolder>{

    private List<list_item1> list_items;
    private Context context;
    private list_item1 temp;
    private Intent intent;


      public MyAdapter1(List<list_item1> list_items, Context context) {
        this.list_items = list_items;
        this.context = context;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item1, parent,false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final list_item1 listItem = list_items.get(position);
        temp = listItem;
        holder.number1.setText(listItem.getNumber1());
        holder.from_date.setText(listItem.getFrom_date());
        holder.to_date.setText(listItem.getTo_date());
        if(listItem.getName1().equals("wt")){
            Picasso.with(context).load(R.mipmap.wt).into(holder.imageView1);
        }else if(listItem.getName1().equals("os")){
            Picasso.with(context).load(R.mipmap.os).into(holder.imageView1);
        }else if(listItem.getName1().equals("flat")){
            Picasso.with(context).load(R.mipmap.flat).into(holder.imageView1);
        }else if(listItem.getName1().equals("cg")){
            Picasso.with(context).load(R.mipmap.cg).into(holder.imageView1);
        }else if(listItem.getName1().equals("dwdm")){
            Picasso.with(context).load(R.mipmap.dwdm).into(holder.imageView1);
        }else if(listItem.getName1().equals("emi")){
            Picasso.with(context).load(R.mipmap.emi).into(holder.imageView1);
        }else if(listItem.getName1().equals("gwcc")){
            Picasso.with(context).load(R.mipmap.gwcc).into(holder.imageView1);
        }else if(listItem.getName1().equals("hvpe")){
            Picasso.with(context).load(R.mipmap.hvpe).into(holder.imageView1);
        }else if(listItem.getName1().equals("ipr")){
            Picasso.with(context).load(R.mipmap.ipr).into(holder.imageView1);
        }else if(listItem.getName1().equals("ish")){
            Picasso.with(context).load(R.mipmap.ish).into(holder.imageView1);
        }else if(listItem.getName1().equals("nmt")){
            Picasso.with(context).load(R.mipmap.nmt).into(holder.imageView1);
        }
        holder.cl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final File file = new File(context.getExternalFilesDir(null),(listItem.getName1()+listItem.getNumber1()+".pdf"));
                if(file.exists()){
                    Uri path = FileProvider.getUriForFile(context,"com.santosh.android.class_app",file);
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    List<ResolveInfo> activities = context.getPackageManager().queryIntentActivities(intent, 0);
                    if (activities.size() > 0)
                        context.startActivity(intent);
                }else{
                    Toast.makeText(context,"Please wait ...",Toast.LENGTH_LONG).show();
                    //downloading the file
                    file_download(listItem.getLink1(),(listItem.getName1()+listItem.getNumber1()+".pdf"));
                }
            }
        });
    }
//  /storage/emulated/0/storage/emulated/0/Android/data/com.santosh.android.class_app/files/wt1.pdf

      @Override
    public int getItemCount() {
        return list_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView number1;
        public TextView from_date;
        public TextView to_date;
        public ImageView imageView1;
        public android.support.constraint.ConstraintLayout cl1;
        public ViewHolder(View itemView) {
            super(itemView);
            number1 = (TextView) itemView.findViewById(R.id.tv1_number);
            from_date = (TextView) itemView.findViewById(R.id.tv1_fromdate);
            to_date = (TextView) itemView.findViewById(R.id.tv1_todate);
            imageView1= (ImageView) itemView.findViewById(R.id.iv1_image);
            cl1 = (android.support.constraint.ConstraintLayout) itemView.findViewById(R.id.constraintlayout1);

        }
    }

      private void file_download(String uRl,String nam) {
          File direct = new File(context.getExternalFilesDir(null),nam);
          DownloadManager mgr = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
          Uri downloadUri = Uri.parse(uRl);
          DownloadManager.Request request = new DownloadManager.Request(
                  downloadUri);
          request.setAllowedNetworkTypes(
                  DownloadManager.Request.NETWORK_WIFI
                          | DownloadManager.Request.NETWORK_MOBILE)
                  .setAllowedOverRoaming(false).setTitle("Downloading class_app files")
                  .setDescription("Something useful. No, really.")
                  .setDestinationInExternalFilesDir(context,null,nam);
          mgr.enqueue(request);
          context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

      }
      private BroadcastReceiver onComplete=new BroadcastReceiver() {
          public void onReceive(Context ctxt, Intent intent) {
              // Do Something
              final File file = new File(context.getExternalFilesDir(null),(temp.getName1()+temp.getNumber1()+".pdf"));

              if(file.exists()){
                  Uri path = FileProvider.getUriForFile(context,"com.santosh.android.class_app",file);
                  intent = new Intent(Intent.ACTION_VIEW);
                  intent.setDataAndType(path, "application/pdf");
                  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                  List<ResolveInfo> activities = context.getPackageManager().queryIntentActivities(intent, 0);
                  if (activities.size() > 0)
                      context.startActivity(intent);
              }
          }
      };
  }