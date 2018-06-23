package com.example.neruppuda.lab4;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Neruppuda on 3/26/2018.
 */

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ArrayList<String> quoteList;
    String authorName;
    public RecyclerAdapter(ArrayList<String> quoteList,String authorName) {
        this.quoteList=quoteList;
        this.authorName=authorName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_child,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.info.setText(quoteList.get(position));
        holder.authorInfo.setText("-"+authorName);
    }

    @Override
    public int getItemCount() {
        return quoteList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView info;
        TextView authorInfo;
        public ViewHolder(View itemView) {
            super(itemView);
            info=(TextView)itemView.findViewById(R.id.info_text);
            authorInfo=(TextView)itemView.findViewById(R.id.author_text);
        }
    }
}
