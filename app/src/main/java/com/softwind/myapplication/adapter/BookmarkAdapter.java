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
import com.softwind.myapplication.activity.MainActivity;
import com.softwind.myapplication.databinding.ItemBookmarkNewsBinding;
import com.softwind.myapplication.models.SavedArticles;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder>{
    private List<SavedArticles> savedArticles;
    private ClickListener clickListener;
    private RemoveListener removeListener;

    public BookmarkAdapter(List<SavedArticles> savedArticles) {
        this.savedArticles = savedArticles;
    }

    public void setClickListener (ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setRemoveListener (RemoveListener removeListener) {
        this.removeListener = removeListener;
    }

    @NonNull
    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookmarkNewsBinding view = ItemBookmarkNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.ViewHolder holder, int position) {
        holder.onBind(savedArticles.get(position));
    }

    @Override
    public int getItemCount() {
        return savedArticles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemBookmarkNewsBinding binding;
        public ViewHolder(@NonNull ItemBookmarkNewsBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void onBind(SavedArticles article) {
            binding.articleTitle.setText(article.getTitle());
            binding.articleTime.setText(article.getPubDate());

            if (article.getImage_url() != null) {
                Glide.with(itemView.getContext()).load(article.getImage_url()).listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        binding.lavBookmark.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.lavBookmark.setVisibility(View.GONE);
                        return false;
                    }
                }).into(binding.articleImage);
            }

            binding.btnRemoveArticle.setOnClickListener(v -> {
                removeListener.onRemoveClicked(article);
            });

            binding.getRoot().setOnClickListener(view -> {
                clickListener.onArticleClicked(article);
            });
        }

    }
    public interface ClickListener {
        void onArticleClicked (SavedArticles article);
    }

    public interface RemoveListener {
        void onRemoveClicked (SavedArticles article);
    }

}
