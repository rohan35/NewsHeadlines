package com.example.myapplication.network

import retrofit2.http.GET
import retrofit2.http.Url


interface NetworkService {
    @GET
    suspend fun getRequest(@Url var1: String): Any
}