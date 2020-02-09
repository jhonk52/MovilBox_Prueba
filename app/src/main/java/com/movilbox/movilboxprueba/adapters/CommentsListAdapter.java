package com.movilbox.movilboxprueba.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.movilbox.movilboxprueba.R;
import com.movilbox.movilboxprueba.models.Comment;

import java.util.List;

public class CommentsListAdapter extends BaseAdapter {

    private Context context;
    private List<Comment> comments;
    private int plantillaLayout;

    public CommentsListAdapter(Context context, List<Comment> comments, int plantillaLayout) {
        this.context = context;
        this.comments = comments;
        this.plantillaLayout = plantillaLayout;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder;

        if (convertView == null) {

            LayoutInflater inflador = LayoutInflater.from(this.context);
            convertView = inflador.inflate(plantillaLayout, null);

            holder = new viewHolder();

            holder.txt_name = convertView.findViewById(R.id.txt_name_plantillaLstPostComments);
            holder.txt_email = convertView.findViewById(R.id.txt_email_plantillaLstPostComments);
            holder.txt_body = convertView.findViewById(R.id.txt_body_plantillaLstPostComments);

            convertView.setTag(holder);

        } else {

            holder = (viewHolder) convertView.getTag();

        }

        holder.txt_name.setText(comments.get(position).getName());
        holder.txt_email.setText(comments.get(position).getEmail());
        holder.txt_body.setText(comments.get(position).getBody());

        return convertView;

    }

    public static class viewHolder {
        public TextView txt_name,txt_email,txt_body;
    }
}