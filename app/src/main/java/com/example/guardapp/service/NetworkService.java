package com.example.guardapp.service;

import java.util.List;

import com.example.guardapp.model.Article;

import io.reactivex.Single;

public interface NetworkService {
    Single<List<Article>> getHeadlineArticles();
}
