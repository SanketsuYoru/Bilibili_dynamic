package com.example.monent_kotlin.datautil

import android.R
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class DataViewModel constructor(application: Application):AndroidViewModel(application)  {
    private var _content=MutableLiveData<List<ContentBlock>>()
    val content : LiveData<List<ContentBlock>>
    get() = _content
    init {
//        content.value= ContentBlock("0")
    }
    // LiveData<List<User>>

    fun setContent(list:List<ContentBlock>){
       _content.value= list
    }

}