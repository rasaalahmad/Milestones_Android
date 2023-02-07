package com.example.milestone2.repository

import androidx.lifecycle.MutableLiveData
import com.example.milestone2.api_resources.MemeAPIClient
import com.example.milestone2.data_classes.Data

class MemeRepository {
    val memeAPIClient = MemeAPIClient()
    fun getMemesData(): MutableLiveData<Data>{
        return memeAPIClient.getMemesData()
    }
}