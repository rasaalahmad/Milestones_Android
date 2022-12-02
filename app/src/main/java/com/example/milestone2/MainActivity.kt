package com.example.milestone2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milestone2.memeclasses.Data
import com.example.milestone2.memeclasses.Meme
import com.example.milestone2.memeclasses.MemeData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {
    lateinit var memesList: Data
<<<<<<< Updated upstream
    lateinit var adapter:MemeViewAdapter
=======
    lateinit var adapter: MemeViewAdapter
    private val gson:Gson = Gson()
    private var memeArray:ArrayList<Meme> = ArrayList()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "milestone")

    private val memeDatafromAPI = stringPreferencesKey("memes_data_from_api")
>>>>>>> Stashed changes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rView:RecyclerView = findViewById(R.id.meme_recycler_view)
        rView.layoutManager = LinearLayoutManager(this)

<<<<<<< Updated upstream
        val sharedPreferences = getSharedPreferences("MileStone", MODE_PRIVATE)

        val gson:Gson = Gson()
        val json = sharedPreferences.getString("Set","")

        if(json!!.isEmpty())
        {
=======
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
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        else
        {
=======

    }


    suspend fun storeDataframeAPI(){
        dataStore.edit { preferences ->
            val gson = Gson()
            val json = gson.toJson(memesList.memes!!)
            preferences[memeDatafromAPI] = json
        }
    }

    private fun getAll(): Flow<ArrayList<Meme>> {
        return dataStore.data.map { preferences ->
            val jsonString = preferences[memeDatafromAPI] ?: "[]"
>>>>>>> Stashed changes
            val type: Type = object : TypeToken<ArrayList<Meme?>?>() {}.type
            val memes:ArrayList<Meme>? = gson.fromJson(json, type)
            //Log.d("APP CHECK 2", memes!!.toString())

            adapter = MemeViewAdapter(memes!!)
            // attaching it with recycler view adapter
            rView.adapter = adapter
        }
    }
}