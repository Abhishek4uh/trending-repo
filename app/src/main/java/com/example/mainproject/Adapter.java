package com.example.mainproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mainproject.models.ItemsItem;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {

    private Context context;
    private final ArrayList<ItemsItem> itemList;
    public Adapter(ArrayList<ItemsItem> list){
        this.itemList = list;
    }
    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.itemview,parent,false);
        return new AdapterViewHolder(view);
    }
    //@SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {

        String desc=itemList.get(position).getDesc();       //Description
        String language=itemList.get(position).getLang();   //Language
        String star=itemList.get(position).getStars();      //Star Count
        String fork=itemList.get(position).getForks();      //Fork Count

        String repo = itemList.get(position).getRepo();     //Repo contains both {Username} and {Repo Name}
        String[] arr = repo.split("/");
        holder.userName.setText(arr[0]);
        holder.repoName.setText(arr[1]);
        holder.description.setText(desc);
        holder.language.setText(language);
        holder.starCount.setText(star);
        holder.forkCount.setText(fork);

        if (itemList.get(position).getLang().isEmpty()){
            holder.language.setText("!Lang specific");
        }
        if (itemList.get(position).getDesc().isEmpty()){
            holder.description.setText("No description available for this repo");
        }
        if (itemList.get(position).getAvatars().isEmpty()){
            Glide
                    .with(context)
                    .load("https://github.githubassets.com/images/modules/open_graph/github-octocat.png")
                    .into(holder.image);
        } else {
            Glide
                    .with(context)
                    .load(itemList.get(position).getAvatars().get(0))
                    .into(holder.image);
        }

        if (itemList.get(position).getExpanded()) {
            holder.starCount.setVisibility(View.VISIBLE);
            holder.language.setVisibility(View.VISIBLE);
            holder.forkCount.setVisibility(View.VISIBLE);
            holder.description.setVisibility(View.VISIBLE);
            holder.langIcon.setVisibility(View.VISIBLE);
            holder.starIcon.setVisibility(View.VISIBLE);
            holder.forkIcon.setVisibility(View.VISIBLE);
        } else {
            holder.starCount.setVisibility(View.GONE);
            holder.language.setVisibility(View.GONE);
            holder.forkCount.setVisibility(View.GONE);
            holder.description.setVisibility(View.GONE);
            holder.langIcon.setVisibility(View.GONE);
            holder.starIcon.setVisibility(View.GONE);
            holder.forkIcon.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean previousValue = itemList.get(holder.getBindingAdapterPosition()).getExpanded();
                itemList.get(holder.getBindingAdapterPosition()).setExpanded(!previousValue);
                notifyItemChanged(holder.getBindingAdapterPosition());
            }
        });

    }
    @Override
    public int getItemCount() {

        return itemList.size();
    }

    static class AdapterViewHolder extends RecyclerView.ViewHolder{

        TextView userName;
        TextView repoName;
        TextView description;
        TextView language;
        TextView starCount;
        TextView forkCount;
        ImageView image;
        ImageView langIcon;
        ImageView starIcon;
        ImageView forkIcon;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.tv1);
            repoName = itemView.findViewById(R.id.tv2);
            image = itemView.findViewById(R.id.imgView1);
            description = itemView.findViewById(R.id.description);
            language = itemView.findViewById(R.id.langTV);
            starCount = itemView.findViewById(R.id.starCount);
            forkCount = itemView.findViewById(R.id.forkCount);
            langIcon = itemView.findViewById(R.id.redButton);
            starIcon = itemView.findViewById(R.id.imgStar);
            forkIcon = itemView.findViewById(R.id.fork);
        }
    }
}


