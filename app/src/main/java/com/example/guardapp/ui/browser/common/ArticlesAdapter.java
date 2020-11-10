package com.example.guardapp.ui.browser.common;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.guardapp.model.Article;

public abstract class ArticlesAdapter<V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    public ArticlesAdapter() {

    }

    private List<Article> articleList = new ArrayList<>();
    protected OnItemClickListener onItemClickListener;

    public void addAll(Collection<? extends Article> articles) {
        articleList.addAll(articles);
        notifyDataSetChanged();
    }

    public void clear() {
        articleList.clear();
    }

    protected Article getItem(int position) {
        return articleList.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull View view, Article article);
    }


    @Override
    public int getItemCount() {
        return articleList.size();
    }

}

