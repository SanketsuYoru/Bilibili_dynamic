package com.example.monent_kotlin

import java.util.*

class ContentBlock constructor(str: String)  {
    private var title:String="init"
    init {
        this.title=str
    }
    fun getTitle():String{
        return this.title
    }
    fun setTitle(str: String){
        this.title=str
    }
}