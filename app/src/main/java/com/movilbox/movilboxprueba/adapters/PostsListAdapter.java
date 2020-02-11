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

public class PostsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Post> posts;
    private int layoutPost;
    private int layoutFooter;
    private OnItemClickListener itemClickListener;

    private static final int FOOTER_VIEW = 1;

    public PostsListAdapter(List<Post> posts, int layoutPost, int layoutFooter, OnItemClickListener itemClickListener) {
        this.posts = posts;
        this.layoutPost = layoutPost;
        this.layoutFooter = layoutFooter;
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (viewType == FOOTER_VIEW){
            view = LayoutInflater.from(parent.getContext()).inflate(layoutFooter,parent,false);
            return new FooterViewHolder(view);
        }
        else{
            view = LayoutInflater.from(parent.getContext()).inflate(layoutPost,parent,false);
            return new PostsViewHolder(view);
        }

    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof FooterViewHolder){

            FooterViewHolder vh = (FooterViewHolder) holder;

        }else if (holder instanceof PostsViewHolder){

            PostsViewHolder vh = (PostsViewHolder) holder;
            vh.bind(posts.get(position<posts.size()?position:posts.size()-1), position ,itemClickListener);
        }

    }



    @Override
    public int getItemCount() {

        if (posts.size()>0){
            return posts.size()+1;
        }else{
            return 0;
        }

    }



    @Override
    public int getItemViewType(int position) {

        if (position == posts.size()){
            return FOOTER_VIEW;
        }

        return super.getItemViewType(position);
    }




    public void deleteItem(int position){
        posts.remove(position);
        notifyItemRemoved(position);
    }




    public void editItem(int position, Post post){
        posts.set(position,post);
        notifyItemChanged(position);
    }




    public void setPosts(List<Post> posts){
        this.posts = posts;
    }




    public static class PostsViewHolder extends RecyclerView.ViewHolder{

        TextView txt_title;
        TextView txt_body;
        ImageView img_indicador,img_favorite;

        public PostsViewHolder(@NonNull View itemView) {
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




    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    posts.clear();
                    notifyDataSetChanged();
                }
            });
        }

    }




    public interface OnItemClickListener {
        void onItemClick(Post post,int position);
    }




}

