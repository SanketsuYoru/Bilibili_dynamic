package com.example.monent_kotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dreawercontent_layout.*
import kotlinx.android.synthetic.main.motion_icon_matching.*
import kotlinx.android.synthetic.main.motion_icon_message.*
import kotlinx.android.synthetic.main.motion_icon_news.*
import kotlinx.android.synthetic.main.motion_icon_social.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val destinationset= mapOf(
            R.id.newsFragment to motion_news,
            R.id.matchingFragment to motion_matching,
            R.id.socialFragment to motion_social,
            R.id.messageFragment to motion_message
        )
        navController=findNavController(R.id.navhost_fragment)
        destinationset.forEach{item->
            item.value.setOnClickListener{navController.navigate(item.key)}
        }

        navController.addOnDestinationChangedListener{controller, destination, arguments ->
            destinationset.forEach{
                it.value.progress=0.000001f
            }
            destinationset[destination.id]?.transitionToEnd()
        }
    }




}