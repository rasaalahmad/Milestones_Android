package com.example.milestone2.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.milestone2.data_classes.Data
import com.example.milestone2.repository.MemeRepository

class MemeViewModel(
    private val memeRepository: MemeRepository
    ):ViewModel() {

    fun getMemes(): MutableLiveData<Data> {
        return memeRepository.getMemesData()
    }
}