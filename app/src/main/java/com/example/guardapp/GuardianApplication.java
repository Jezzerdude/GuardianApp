package com.example.guardapp;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import com.example.guardapp.service.GuardNetworkService;
import com.example.guardapp.service.NetworkService;
import com.example.guardapp.network.interceptors.CacheInterceptor;
import com.example.guardapp.network.interceptors.ConnectivityInterceptor;
import com.example.guardapp.repository.ArticlesRepositoryImpl;
import okhttp3.Interceptor;


public class GuardianApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        initializeSingletons();
    }

    void initializeSingletons() {
        Interceptor connectivityInterceptor = new ConnectivityInterceptor(this);
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        cacheInterceptor.setCacheDirectory(getCacheDir());
        NetworkService networkService = new GuardNetworkService(connectivityInterceptor, cacheInterceptor);
        ArticlesRepositoryImpl.initRepository(networkService);
    }
}
