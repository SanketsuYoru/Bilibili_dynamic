package com.example.monent_kotlin.datautil

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class volleySingleton private constructor (context: Context){
    companion object{
        private var INSTANCE:volleySingleton?=null
        fun getInstance(context: Context)=
            INSTANCE?: synchronized(this){volleySingleton(context).also { INSTANCE=it }}
    }

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
}