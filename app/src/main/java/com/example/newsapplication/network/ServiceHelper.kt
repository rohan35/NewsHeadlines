package com.example.newsapplication.network

class ServiceHelper(private val networkService: NetworkService) {
    suspend fun get(url:String) = networkService.getRequest(url)
}