package com.example.newsapplication.data.repositories

import com.example.newsapplication.network.ServiceHelper

class AppRepo(private val serviceHelper: ServiceHelper) {
    /*
       get call to make a netwotrk call and give live data
        */
    suspend fun getCall(
        subUrl: String,
        params:HashMap<String, String>
    ): Any {
        return serviceHelper.get(subUrl,params)
    }
}