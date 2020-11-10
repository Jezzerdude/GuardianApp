package com.example.guardapp.ui.browser.discover.trending;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.guardapp.repository.ArticlesRepositoryImpl;

class TrendingViewModelFactory implements ViewModelProvider.Factory {
    final private ArticlesRepositoryImpl articlesRepository;

    TrendingViewModelFactory(ArticlesRepositoryImpl articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TrendingViewModel(articlesRepository);
    }
}
