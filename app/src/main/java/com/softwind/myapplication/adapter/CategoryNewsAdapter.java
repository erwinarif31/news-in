package com.softwind.myapplication.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.softwind.myapplication.databinding.ItemDiscoverNewsBinding;
import com.softwind.myapplication.models.Article;

import java.util.List;

public class CategoryNewsAdapter extends RecyclerView.Adapter<CategoryNewsAdapter.ViewHolder> {
    private List<Article> articleList;
    public ClickListener clickListener;

    public CategoryNewsAdapter(List<Article> articleList) {
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

        public void onBind(Article article) {
            binding.articleTitle.setText(article.getTitle());
            binding.articleTime.setText(article.getDateDiff());
            if (article.getImage_url() != null) {
//                Glide.with(itemView.getContext()).load(article.getImage_url()).into(binding.breakingNewsImage);
                Glide.with(itemView.getContext()).load(article.getImage_url()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        Toast.makeText(itemView.getContext(), "Oops. There's a problem with your network!", Toast.LENGTH_SHORT).show();
                        binding.breakingNewsImageLoading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.breakingNewsImageLoading.setVisibility(View.GONE);
                        return false;
                    }

                }).into(binding.ivArticleImage);
            }

            binding.getRoot().setOnClickListener(v -> clickListener.onClick(article));
        }
    }

    public interface ClickListener {
        void onClick(Article article);
    }
}
