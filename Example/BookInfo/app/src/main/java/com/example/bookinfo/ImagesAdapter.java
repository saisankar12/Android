package com.example.bookinfo;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesHolder> {

    Context context;
    ArrayList<Model>  models;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String t,a,p;

    public ImagesAdapter(Books books, ArrayList<Model> arrayListModel) {
        this.context=books;
        this.models=arrayListModel;
    }

    @NonNull
    @Override
    public ImagesAdapter.ImagesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view =LayoutInflater.from(context).inflate(R.layout.design,viewGroup,false);
       StringBuffer stringBuffer=new StringBuffer();

           t=models.get(i).getTitle();
           a=models.get(i).getAuthor();
           p=models.get(i).getPublished_date();
           stringBuffer.append(t+"\t");
           stringBuffer.append(a+"\t");
           stringBuffer.append(p+"\n");

       preferences=context.getSharedPreferences(context.getString(R.string.details),Context.MODE_PRIVATE);
       editor=preferences.edit();
       editor.putString(context.getString(R.string.data),stringBuffer.toString());
       editor.apply();
       Intent intent=new Intent(context,BookWidget.class);
       intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
       int[] ids=AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context.getApplicationContext(),BookWidget.class));
       intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
       context.sendBroadcast(intent);
        return new ImagesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesAdapter.ImagesHolder imagesHolder, int i) {
        final Model model=models.get(i);
        Picasso.with(context).load(model.getThumbnail()).placeholder(R.drawable.ic_launcher_foreground).into(imagesHolder.imageView);
        imagesHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] book_details=new String[6];
                book_details[0]=model.getId();
                book_details[1]=model.getTitle();
                book_details[2]=model.getAuthor();
                book_details[3]=model.getThumbnail();
                book_details[4]=model.getPublished_date();
                book_details[5]=model.getPreview();
                Intent intent=new Intent(context,BookActivity.class);
                intent.putExtra(context.getString(R.string.book),book_details);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ImagesHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ImagesHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img);
        }
    }
}
