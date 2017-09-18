package com.example.user.instogram;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 13-Sep-17.
 */

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.MyViewHolder>{
    List<ParseObject> parseObjectList;
    Context context;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    //untuk menggunakan context, context dijadikan constructor terlebih dahulu
    public AdapterRecyclerView(List<ParseObject> parseObjectList, Context context) {
        this.parseObjectList = parseObjectList; //mengambil data json
        this.context = context;
    }

    //
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final ParseObject obj = parseObjectList.get(position);
        if(obj.getParseFile("image").getUrl() != ""){
            Picasso.with(context).load(obj.getParseFile("image").getUrl()).into(holder.imageView);
        }

        //get date from parse


        holder.title.setText(obj.getCreatedAt().toString());
        holder.content.setText(obj.getString("caption"));
    }

    @Override
    public int getItemCount() {
        return parseObjectList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title;
        TextView content;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.view_content);
            title = (TextView)itemView.findViewById(R.id.txt_title);
            content = (TextView)itemView.findViewById(R.id.txt_content);
        }

    }


}
