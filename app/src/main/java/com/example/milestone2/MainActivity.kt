package com.example.milestone2

import android.content.Context
import android.os.Bundle
import android.util.Log
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


class MainActivity : AppCompatActivity() {
    lateinit var memesList: Data
    lateinit var adapter:MemeViewAdapter
    private val gson:Gson = Gson()
    //lateinit var memestr:ArrayList<Meme>
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "milestone")

    private val memeDatafromAPI = stringPreferencesKey("memes_data_from_api")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rView:RecyclerView = findViewById(R.id.meme_recycler_view)
        rView.layoutManager = LinearLayoutManager(this)

        val sharedPreferences = getSharedPreferences("MileStone", MODE_PRIVATE)

        runBlocking{
            launch {
                Log.d("DataStore Check: ", "I am here above if")
                Log.d("DataStore Check: ", getAll().toString())
            }
        }

        val json = sharedPreferences.getString("Set","")

        if(json!!.isEmpty())
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
                            launch {
                                Log.d("DataStore Check: ", "I am here")
                                storeDataframeAPI()
                            }
                        }

                        // Creating an Editor object to edit(write to the file)
                        val editor = sharedPreferences.edit()


                        val json = gson.toJson(memesList.memes!!)
                        editor.putString("Set", json );
                        //Log.d("APP CHECK", json.toString())
                        // Storing the key and its value as the data fetched from edittext

                        // Once the changes have been made,
                        // we need to commit to apply those changes made,
                        // otherwise, it will throw an error

                        // Once the changes have been made,
                        // we need to commit to apply those changes made,
                        // otherwise, it will throw an error
                        editor.apply()
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
        else
        {
            val type: Type = object : TypeToken<ArrayList<Meme?>?>() {}.type
            val memes:ArrayList<Meme>? = gson.fromJson(json, type)
            //Log.d("APP CHECK 2", memes!!.toString())

            adapter = MemeViewAdapter(memes!!)
            // attaching it with recycler view adapter
            rView.adapter = adapter
        }
    }

    suspend fun storeDataframeAPI(){
        dataStore.edit { preferences ->
            val gson:Gson = Gson()
            val json = gson.toJson(memesList.memes!!)
            preferences[memeDatafromAPI] = json
        }
    }

    fun getAll(): Flow<ArrayList<Meme>> {
        return dataStore.data.map { preferences ->
            val jsonString = preferences[memeDatafromAPI] ?: "[]"
            val type: Type = object : TypeToken<ArrayList<Meme?>?>() {}.type
            val memes:ArrayList<Meme>? = gson.fromJson(jsonString, type)
            Log.d("DataStore Check first: ", memes!!.toString())
            memes
        }
    }
}