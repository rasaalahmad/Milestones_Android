package com.example.milestone2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milestone2.memeclasses.Data
import com.example.milestone2.memeclasses.MemeData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var memesList: Data
    lateinit var adapter:MemeViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rView:RecyclerView = findViewById(R.id.meme_recycler_view)
        rView.layoutManager = LinearLayoutManager(this)

        // calling get method from api
        val call: Call<MemeData> = MemeAPI.getInstance().create(MemeAPIEndpointInterface::class.java).getMemeData()
        // Asynchronously send the request and notify callback of its response
        // or if an error occurred talking to the server, creating the request, or processing the response.
        call.enqueue(object : Callback<MemeData> {
            override fun onResponse(
                call: Call<MemeData>,
                response: Response<MemeData>
            ) {
                if (response.isSuccessful) {
                    memesList = response.body()?.data!!
                    // adding list to the custom meme adapter
                    adapter = memesList.memes?.let { MemeViewAdapter(it) }!!
                    // attaching it with recycler view adapter
                    rView.adapter = adapter
                }
                else
                {
                    //Log.d("Test: ", "Not successfully")
                    Toast.makeText(applicationContext, "Connection Error, Please try again later", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<MemeData>, t: Throwable) {
                Toast.makeText(applicationContext, "Connection Error, Please try again later", Toast.LENGTH_LONG).show()
            }
        })
    }
}