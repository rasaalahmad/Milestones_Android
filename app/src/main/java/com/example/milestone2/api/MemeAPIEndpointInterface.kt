package com.example.milestone2.api

import com.example.milestone2.memeclasses.MemeData
import retrofit2.Call
import retrofit2.http.GET

interface MemeAPIEndpointInterface {
    @GET("get_memes")
    fun getMemeData(): Call<MemeData>
}