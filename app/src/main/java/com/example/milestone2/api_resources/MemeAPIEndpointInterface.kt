package com.example.milestone2.api_resources

import com.example.milestone2.data_classes.MemeData
import retrofit2.Call
import retrofit2.http.GET

interface MemeAPIEndpointInterface {
    @GET("get_memes")
    fun getMemeData(): Call<MemeData>
}