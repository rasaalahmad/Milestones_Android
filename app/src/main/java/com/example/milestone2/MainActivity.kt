package com.example.milestone2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.milestone2.memeclasses.MemeData
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val memeApi = MemeAPI.getInstance().create(MemeAPIEndpointInterface::class.java)
        val call: Call<MemeData> = MemeAPI.getInstance().create(MemeAPIEndpointInterface::class.java).getMemeData()
        call!!.enqueue(object : Callback<MemeData> {
            override fun onResponse(
                call: Call<MemeData>,
                response: Response<MemeData>
            ) {
                if (response.isSuccessful) {
                    Log.d("Test: ", response.body()!!.data?.memes.toString())
                }
                else
                {
                    Log.d("Test: ", "Not successfully")
                }
            }

            override fun onFailure(call: Call<MemeData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}