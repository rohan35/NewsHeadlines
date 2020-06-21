package com.example.newsapplication.network

class ServiceHelper(private val networkService: NetworkService) {
    suspend fun get(url:String,params:HashMap<String, String>) = networkService.getRequest(url,params)
}