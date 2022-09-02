package com.example.mainproject.retrofit;

import com.example.mainproject.models.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiHelper {

    String url = "repo";

    @GET(url)
    Call<ResponseModel> makeCall();

}
