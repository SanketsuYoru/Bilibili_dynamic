package com.example.monent_kotlin

import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.monent_kotlin.datautil.loadDataUtil
import com.example.monent_kotlin.datautil.volleySingleton
import kotlinx.android.synthetic.main.motion_icon_matching.*
import kotlinx.android.synthetic.main.motion_icon_message.*
import kotlinx.android.synthetic.main.motion_icon_news.*
import kotlinx.android.synthetic.main.motion_icon_social.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private var exitTime: Long = 0
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
            item.value.setOnClickListener{
                //if popBackStack has the destination then pop Backstack,else navigate to the destination
                var isInstack= navController.popBackStack(item.key,false)
                if(!isInstack)
                navController.navigate(item.key)
            }

        }

        navController.addOnDestinationChangedListener{controller, destination, arguments ->
            destinationset.forEach{
                it.value.progress=0.000001f
            }
            //navController.popBackStack()
            destinationset[destination.id]?.transitionToEnd()
        }

        loadDataUtil.request= volleySingleton.getInstance(this.applicationContext).requestQueue
    }





    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {



        var tag=navController.currentDestination.toString()
        if(keyCode==KeyEvent.KEYCODE_BACK&&tag != "Destination(com.example.monent_kotlin:id/detil_layout) label=fragment_detil_layout class=com.example.monent_kotlin.detil_layout")
        {
            if(exit())
               {
                   finish()
                   exitProcess(0)
               }
            return false
        }
        return super.onKeyDown(keyCode, event)
    }
    private fun exit():Boolean {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(
                getApplicationContext(),
                "再按一次退出程序", Toast.LENGTH_SHORT
            ).show()
            exitTime = System.currentTimeMillis()
            return false
        }
            else
            return true
        }

}