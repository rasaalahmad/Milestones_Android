package com.example.milestone2.api_resources

import androidx.lifecycle.MutableLiveData
import com.example.milestone2.classes.Data
import com.example.milestone2.classes.MemeData
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemeAPIClient {
    private var memeAPIEndpointInterface: MemeAPIEndpointInterface? = MemeAPI.getInstance().create(MemeAPIEndpointInterface::class.java)
    private var memeData = MutableLiveData<Data>()

    fun getMemesData():MutableLiveData<Data> = runBlocking{
        memeAPIEndpointInterface!!.getMemeData().enqueue(object : Callback<MemeData> {
            override fun onResponse(
                call: Call<MemeData>,
                response: Response<MemeData>
            ) {
                if (response.isSuccessful) {
                    memeData.value = response.body()?.data!!
                }
            }

            override fun onFailure(call: Call<MemeData>, t: Throwable) {
                //memeData = null
            }
        })
        return@runBlocking memeData
    }
}