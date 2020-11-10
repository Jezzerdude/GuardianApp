package com.example.guardapp.service;


import com.example.guardapp.network.interceptors.networkresponse.articlesresponse.ArticlesResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

interface TheGuardianAPI {
    @GET("search")
    Single<ArticlesResponse> getHeadlineArticles(@Query("tags") String tags, @Query("page-size") int pageSize, @Query("show-fields") String fieldsToShow);


}
