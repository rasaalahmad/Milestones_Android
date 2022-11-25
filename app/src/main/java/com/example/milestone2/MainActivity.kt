package com.example.milestone2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milestone2.memeclasses.Data
import com.example.milestone2.memeclasses.MemeData
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var memesList: Data
    val memesModelList:ArrayList<MemeModel> = ArrayList()
    lateinit var adapter:MemeViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rView:RecyclerView = findViewById(R.id.meme_recycler_view)
        rView.layoutManager = LinearLayoutManager(this)

        //val memeApi = MemeAPI.getInstance().create(MemeAPIEndpointInterface::class.java)
        val call: Call<MemeData> = MemeAPI.getInstance().create(MemeAPIEndpointInterface::class.java).getMemeData()
        call!!.enqueue(object : Callback<MemeData> {
            override fun onResponse(
                call: Call<MemeData>,
                response: Response<MemeData>
            ) {
                if (response.isSuccessful) {
                    memesList = response.body()!!.data!!

                    for ( item in memesList?.memes?.indices!!)
                    {
                        memesModelList.add(MemeModel(memesList?.memes!![item]?.name, memesList?.memes!![item]?.url))
                    }

                    adapter= MemeViewAdapter(memesModelList)
                    rView?.adapter = adapter
                    //Log.d("Test: ", memesList?.memes?.size.toString())
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