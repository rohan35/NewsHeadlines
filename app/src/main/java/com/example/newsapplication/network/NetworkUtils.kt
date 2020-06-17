package com.example.newsapplication.network

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.GsonBuilder

class NetworkUtils {
    companion object
    {
        /**
         * Conversion function to convert string to model class
         * @param response - json string from the api
         * @param modelClass - class to be converted in
         * @return JsonObject
         */
        fun <Any> getModelFromJsonString(response: String? = "", modelClass: Class<Any>): Any? {
            return try {
                val gson = GsonBuilder().create()
                gson.fromJson(response, modelClass)
            } catch (var3: Exception) {
                var3.printStackTrace()
                null
            }

        }
        fun verifyAvailableNetwork(context: Context):Boolean{
            val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo=connectivityManager.activeNetworkInfo
            return  networkInfo!=null && networkInfo.isConnected
        }
    }
}