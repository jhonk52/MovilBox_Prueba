package com.movilbox.movilboxprueba.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.movilbox.movilboxprueba.models.Post;
import com.movilbox.movilboxprueba.R;

import java.util.List;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.ViewHolder>{

    private List<Post> posts;
    private int layout;
    private OnItemClickListener itemClickListener;

    public PostsListAdapter(List<Post> posts, int layout, OnItemClickListener itemClickListener) {
        this.posts = posts;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(posts.get(position), position ,itemClickListener);
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_title;
        TextView txt_body;
        ImageView img_indicador;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txt_title = itemView.findViewById(R.id.txt_title_templateRcvPostList);
            this.txt_body = itemView.findViewById(R.id.txt_body_templateRcvPostList);
            this.img_indicador = itemView.findViewById(R.id.img_indicador_templateRcvPostList);
        }

        public void bind (final Post post, final int position, final OnItemClickListener listener){

            this.txt_title.setText(post.getTitle());
            this.txt_body.setText(post.getBody());

            if( position < 20 && !post.getViewed()) {
                this.img_indicador.setVisibility(View.VISIBLE);
            }else{
                this.img_indicador.setVisibility(View.INVISIBLE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(post,getAdapterPosition());
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Post post,int position);
    }

}

