package com.example.myapplication;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import model.ImageName;

public class ImageNameAdapter extends
        RecyclerView.Adapter<ImageNameAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        //private ImageView mImageHero;
        private TextView mTextName;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //mImageHero = itemView.findViewById(R.id.image_hero);
            mTextName = itemView.findViewById(R.id.text_name);
            cardView = itemView.findViewById(R.id.imageCardView);
        }
    }
    private Context mContext;
    private ArrayList<ImageName> mImagenames;
    private SelectListener listener;

    public ImageNameAdapter(Context mContext, ArrayList<ImageName> mImagenames, SelectListener listener) {
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
        ImageName hero = mImagenames.get(position);
//        Glide.with(mContext)
//                .load(hero.getImage())
//                .into(holder.mImageHero);
        holder.mTextName.setText(hero.getName());
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

