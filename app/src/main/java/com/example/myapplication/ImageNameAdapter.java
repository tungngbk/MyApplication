package com.example.myapplication;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.ImageName;
import model.record;

public class ImageNameAdapter extends
        RecyclerView.Adapter<ImageNameAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageHero;
        private TextView mTextName;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageHero = itemView.findViewById(R.id.image_hero);
            mTextName = itemView.findViewById(R.id.text_name);
            cardView = itemView.findViewById(R.id.imageCardView);
        }
    }
    private Context mContext;
    private ArrayList<record> mImagenames;
    private SelectListener listener;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public ImageNameAdapter(Context mContext, ArrayList<record> mImagenames, SelectListener listener) {
        this.mContext = mContext;
        this.mImagenames = mImagenames;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View heroView = inflater.inflate(R.layout.item_hero, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        record hero = mImagenames.get(position);
        String date = hero.getDate();
        try {
            Date date1 = dateFormat.parse(date);
            holder.mTextName.setText(dateFormat2.format(date1));
        } catch (ParseException e) {
            holder.mTextName.setText(date);
            throw new RuntimeException(e);
        }

        byte[] bytes = Base64.decode(hero.getSegment_image(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Glide.with(mContext)
                .load(bitmap)
                .into(holder.mImageHero);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(mImagenames.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImagenames.size();
    }

}


