package com.example.monent_kotlin.datautil

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable

object util {
    fun dp2px(context: Context, dp: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun px2dp(context: Context, px: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }
    fun getRandomColor():GradientDrawable{
        var color= GradientDrawable()
        color.setColor(Color.argb(255, (0..200).random(), (0..200).random(), (0..200).random()))
        color.setSize(200,200)
        return color
    }

}