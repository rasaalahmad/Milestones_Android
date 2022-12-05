package com.example.milestone2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milestone2.memeclasses.Data
import com.example.milestone2.memeclasses.Meme
import com.example.milestone2.memeclasses.MemeData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import androidx.lifecycle.lifecycleScope
import com.example.milestone2.adaptars.MemeViewAdapter
import com.example.milestone2.api.MemeAPI
import com.example.milestone2.api.MemeAPIEndpointInterface


class MainActivity : AppCompatActivity() {
    lateinit var memesList: Data
    lateinit var adapter: MemeViewAdapter
    private val gson:Gson = Gson()
    private var memeArray:ArrayList<Meme> = ArrayList()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "milestone")

    private val memeDatafromAPI = stringPreferencesKey("memes_data_from_api")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rView:RecyclerView = findViewById(R.id.meme_recycler_view)
        rView.layoutManager = LinearLayoutManager(this)

        val startBtn: Button = findViewById(R.id.startapp)

        lifecycleScope.launch{
            getAll().collect{
                it.forEach{meme ->
                    memeArray.add(meme)
                }

                adapter = MemeViewAdapter(memeArray)
                // attaching it with recycler view adapter
                rView.adapter = adapter
                if(memeArray.isNotEmpty())
                    startBtn.visibility = View.GONE
                Log.d("Flow collect in scope", memeArray.toString())

            }
        }

        startBtn.setOnClickListener {
            startBtn.visibility = View.GONE
            apiCallAndResponse(rView)
        }
    }


    suspend fun storeDatafromAPI(){
        dataStore.edit { preferences ->
            val gson = Gson()
            val json = gson.toJson(memesList.memes!!)
            preferences[memeDatafromAPI] = json
        }
    }

    private fun getAll(): Flow<ArrayList<Meme>> {
        return dataStore.data.map { preferences ->
            val jsonString = preferences[memeDatafromAPI] ?: "[]"
            val type: Type = object : TypeToken<ArrayList<Meme?>?>() {}.type
            val memes:ArrayList<Meme>? = gson.fromJson(jsonString, type)
            memes!!
        }
    }

    private fun apiCallAndResponse(rView:RecyclerView)
    {
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

                    runBlocking{
                        storeDatafromAPI()
                    }
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