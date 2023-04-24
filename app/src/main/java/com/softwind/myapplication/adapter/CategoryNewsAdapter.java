package com.softwind.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softwind.myapplication.databinding.ItemDiscoverNewsBinding;
import com.softwind.myapplication.models.SavedArticles;
import com.softwind.myapplication.util.Content;

import java.util.List;

public class CategoryNewsAdapter extends RecyclerView.Adapter<CategoryNewsAdapter.ViewHolder> {
    private List<SavedArticles> articleList;
    public ClickListener clickListener;

    public CategoryNewsAdapter(List<SavedArticles> articleList) {
        this.articleList = articleList;
    }

    public void setClickListener (ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CategoryNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDiscoverNewsBinding view = ItemDiscoverNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryNewsAdapter.ViewHolder holder, int position) {
        holder.onBind(articleList.get(position));
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemDiscoverNewsBinding binding;
        public ViewHolder(@NonNull ItemDiscoverNewsBinding view) {
            super(view.getRoot());
            this.binding = view;
        }

        public void onBind(SavedArticles article) {
            binding.articleTitle.setText(article.getTitle());
            binding.articleTime.setText(article.getPubDate());
            if (article.getImage_url() != null) {
                Content.placeImage(binding.getRoot().getContext(), article, binding.ivArticleImage, binding.breakingNewsImageLoading);
            } else {
                Content.setFailedResource(article);
                Content.placeImage(binding.getRoot().getContext(), article, binding.ivArticleImage, binding.breakingNewsImageLoading);
            }

            binding.getRoot().setOnClickListener(v -> clickListener.onClick(article));
        }
    }

    public interface ClickListener {
        void onClick(SavedArticles article);
    }
}
