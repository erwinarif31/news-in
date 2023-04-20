package com.softwind.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softwind.myapplication.R;
import com.softwind.myapplication.models.Article;

import java.util.List;

public class BreakingNewsAdapter extends RecyclerView.Adapter<BreakingNewsAdapter.ViewHolder> {

    private List<Article> breakingNewsList;

    public BreakingNewsAdapter(List<Article> breakingNewsList) {
        this.breakingNewsList = breakingNewsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_breaking_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BreakingNewsAdapter.ViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return breakingNewsList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

