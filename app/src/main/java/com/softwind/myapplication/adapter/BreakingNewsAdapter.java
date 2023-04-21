package com.softwind.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.softwind.myapplication.databinding.ItemBreakingNewsBinding;
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
        ItemBreakingNewsBinding view = ItemBreakingNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BreakingNewsAdapter.ViewHolder holder, int position) {
        holder.onBind(breakingNewsList.get(position));
    }



    @Override
    public int getItemCount() {
        return breakingNewsList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemBreakingNewsBinding binding;
        public ViewHolder(@NonNull ItemBreakingNewsBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void onBind(Article article) {
            binding.tvBreakingNewsTitle.setText(article.getTitle());
            binding.tvPubDate.setText(article.getDateDiff());
            if (article.getImage_url() != null) {
                Glide.with(itemView.getContext()).load(article.getImage_url()).into(binding.breakingNewsImage);
            }
        }
    }
}

