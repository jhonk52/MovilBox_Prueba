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

    private Post deletedPost;
    private int positionDeletedPost;

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


    public void deleteItem(int position){

        deletedPost = posts.get(position);
        positionDeletedPost = position;
        posts.remove(position);
        notifyItemRemoved(position);

    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_title;
        TextView txt_body;
        ImageView img_indicador,img_favorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txt_title = itemView.findViewById(R.id.txt_title_templateRcvPostList);
            this.txt_body = itemView.findViewById(R.id.txt_body_templateRcvPostList);
            this.img_indicador = itemView.findViewById(R.id.img_indicador_templateRcvPostList);
            this.img_favorite = itemView.findViewById(R.id.img_favorite_templateRcvPostList);
        }

        public void bind (final Post post, final int position, final OnItemClickListener listener){

            this.txt_title.setText(post.getTitle());
            this.txt_body.setText(post.getBody());

            if( position < 20 && !post.getViewed()) {
                this.img_indicador.setVisibility(View.VISIBLE);
            }else{
                this.img_indicador.setVisibility(View.INVISIBLE);
            }

            if(post.getFavorite()){
                this.img_favorite.setVisibility(View.VISIBLE);
            }else {
                this.img_favorite.setVisibility(View.GONE);
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

