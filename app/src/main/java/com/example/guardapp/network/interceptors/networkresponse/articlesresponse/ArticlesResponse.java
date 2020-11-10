package com.example.guardapp.network.interceptors.networkresponse.articlesresponse;

import com.google.gson.annotations.SerializedName;


public class ArticlesResponse {
    @SerializedName("response")
    private com.example.guardapp.network.interceptors.networkresponse.articlesresponse.Response response;

    public Response getResponse() {
        return response;
    }

}