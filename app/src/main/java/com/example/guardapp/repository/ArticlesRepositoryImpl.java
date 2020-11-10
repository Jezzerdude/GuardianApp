package com.example.guardapp.repository;

import java.util.List;
import java.util.concurrent.TimeUnit;


import com.example.guardapp.model.Article;
import com.example.guardapp.service.NetworkService;

import io.reactivex.Observable;

public class ArticlesRepositoryImpl {
    private final NetworkService networkService;

    private static ArticlesRepositoryImpl INSTANCE;
    private int syncPeriod = 30;

    private ArticlesRepositoryImpl(NetworkService networkService) {
        this.networkService = networkService;
    }

    public static ArticlesRepositoryImpl initRepository(NetworkService networkService) {
        if (INSTANCE != null) {
            throw new RuntimeException("you can't reinitialize ArticlesRepository, Already is.");
        } else {
            INSTANCE = new ArticlesRepositoryImpl(networkService);
            return INSTANCE;
        }
    }

    public static ArticlesRepositoryImpl getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        } else {
            throw new RuntimeException("you have to initialize the repository first.");
        }
    }

    public Observable<List<Article>> getTrendingArticles() {
        return Observable.interval(0, syncPeriod, TimeUnit.MINUTES)
                .flatMap((time) -> networkService
                        .getHeadlineArticles()
                        .toObservable());
    }


    public void setSyncPeriod(int syncPeriod) {
        this.syncPeriod = syncPeriod;
    }
}
