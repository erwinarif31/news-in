package com.softwind.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.softwind.myapplication.databinding.ItemBreakingNewsBinding;
import com.softwind.myapplication.models.Article;

import java.util.List;

public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.ViewHolder> {

    private List<Article> breakingNewsList;
    public ClickListener clickListener;
    public HomeFragmentAdapter(List<Article> breakingNewsList) {
        this.breakingNewsList = breakingNewsList;
    }

    public void setClickListener (ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBreakingNewsBinding view = ItemBreakingNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFragmentAdapter.ViewHolder holder, int position) {
        holder.onBind(breakingNewsList.get(position));
    }



    @Override
    public int getItemCount() {
        return breakingNewsList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
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
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onArticleClicked(article);
                }
            });
        }
    }

    public interface ClickListener {
        void onArticleClicked (Article article);
    }
}

