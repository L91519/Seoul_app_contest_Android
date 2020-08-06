package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(){
    val _message = MutableLiveData<String>()

    fun setMessage(msg: String) {
        _message.postValue(msg)
    }
}