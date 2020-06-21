package com.example.newsapplication.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url


interface NetworkService {
    @GET
    suspend fun getRequest(@Url var1: String, @QueryMap params:HashMap<String, String>): Any
}