package com.example.milestone2

import com.example.milestone2.memeclasses.MemeData
import retrofit2.Call
import retrofit2.http.GET

interface MemeAPIEndpointInterface {
    @GET(".") // . because we use the same link of api to get data
    fun getMemeData(): Call<MemeData>
}