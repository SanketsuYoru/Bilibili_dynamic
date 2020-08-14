package com.example.monent_kotlin.datautil

import android.graphics.drawable.GradientDrawable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContentBlock constructor(var usericon:String,var username:String,var descripition: String, var uri:MutableList<Bilibili_dynamic_card.ItemBean.PicturesBean>,var type:String) : Parcelable {
    private var _description:String=""
    private var _username:String=""
    private var _userProfileicon=""
    private var _pictures:MutableList<Bilibili_dynamic_card.ItemBean.PicturesBean>
    private var placeHolder: GradientDrawable? =null
    private var liked:Boolean=false
    private var _type:String=""
    init {
        this._description=descripition
        this._pictures=uri
        this._username= username
        this._userProfileicon=usericon
        this._type=type
    }
    fun getimgUrl():MutableList<Bilibili_dynamic_card.ItemBean.PicturesBean>{
        return this._pictures
    }
    fun getplaceholder(): GradientDrawable? {
        return placeHolder
    }
    fun setplaceholder(placeholder: GradientDrawable){
        this.placeHolder=placeholder
    }
    fun getTitle():String{
        return this._description
    }
    fun setLike_Unlike(like:Boolean)
    {
        this.liked=like
    }
    fun setPicurls(urls:MutableList<Bilibili_dynamic_card.ItemBean.PicturesBean>){
        this._pictures=urls
    }

    fun isLiked():Boolean{
        return this.liked
    }
    fun setTitle(str: String){
        this._description=str
    }
}