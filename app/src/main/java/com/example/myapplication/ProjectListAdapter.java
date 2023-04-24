package com.example.myapplication;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import model.Project;

public class ProjectListAdapter extends
        RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextName;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextName = itemView.findViewById(R.id.text_name);
            cardView = itemView.findViewById(R.id.imageCardView);
        }
    }
    private Context mContext;
    private ArrayList<Project> mProject;
    private SelectListenerProject listener;

    public ProjectListAdapter(Context mContext, ArrayList<Project> mProject, SelectListenerProject listener) {
        this.mContext = mContext;
        this.mProject = mProject;
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
        Project project = mProject.get(position);
        holder.mTextName.setText(project.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(mProject.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProject.size();
    }

}


