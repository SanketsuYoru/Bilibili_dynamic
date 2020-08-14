package com.example.monent_kotlin.datautil

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.model.ModelLoader
import com.example.monent_kotlin.R
import kotlinx.android.synthetic.main.motion_like.view.*


class RecAdapter:ListAdapter<ContentBlock,MyViewHolder>(DIFFCALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.content_cell,parent,false))
    }

    object DIFFCALLBACK: DiffUtil.ItemCallback<ContentBlock>() {
        override fun areItemsTheSame(oldItem: ContentBlock,newItem: ContentBlock): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: ContentBlock, newItem: ContentBlock): Boolean {
            return oldItem.descripition == newItem.descripition
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //载入基本数据
        holder.cell_cardView.removeAllViews()
        holder.cell_userCommit.text=getItem(position)?.getTitle()
        holder.cell_userName.text=getItem(position)?.username
        var usericon=getItem(position)?.usericon
        if(usericon!=null)
            loadDataUtil.loadImage(usericon,holder.itemView.context,holder.cell_Userimage)


        if(getItem(position).isLiked())
            holder.cell_like.setImageResource(R.drawable.ic_like_selected)
        else
            holder.cell_like.setImageResource(R.drawable.ic_like_unselected)
        holder.itemView.like_motionLayout.setOnClickListener {
            if(getItem(position).isLiked())
            {
                Toast.makeText(
                    holder.itemView.context,
                    "unliked user comment ${position.toString()}", Toast.LENGTH_SHORT
                ).show()
                getItem(position).setLike_Unlike(false)
                holder.itemView.like_motionLayout.transitionToStart()
                holder.cell_like.setImageResource(R.drawable.ic_like_unselected)
            }

            else
            {
                Toast.makeText(
                    holder.itemView.context,
                    "liked user comment ${position.toString()}", Toast.LENGTH_SHORT
                ).show()
                getItem(position).setLike_Unlike(true)
                holder.itemView.like_motionLayout.transitionToEnd()
                holder.cell_like.setImageResource(R.drawable.ic_like_selected)
            }



        }



        //get random color and set image's placeholder
        var placeholder= util.getRandomColor()
        getItem(position)?.getplaceholder()?:getItem(position)?.setplaceholder(placeholder)
        //load image if url exist
        var imageUri=getItem(position)?.getimgUrl()
        if(!imageUri.isNullOrEmpty())
        {
            var context=holder.itemView.context
            var linearlayout= LinearLayout(context)
            var layout=LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,util.px2dp(context,1200f))
            //set middle divider
            val width = 10
            val drawable = GradientDrawable()
            drawable.setSize(width, 1)
            linearlayout.dividerDrawable = drawable
            linearlayout.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            linearlayout.layoutParams= layout
            //load images
            for (count in 0 until imageUri.count()){
                var imView=loadDataUtil.loadImage_dynamic(imageUri?.get(count).img_src,context,getItem(position)?.getplaceholder()!!)
                imView.setOnClickListener {
                    Bundle().apply {
                        putInt("index",count)
                        putString("url",imageUri?.get(count).img_src)
                        putParcelable("item",getItem(position))
                        holder.itemView.findNavController().navigate(R.id.detil_layout,this)
                    }
                }

                linearlayout.addView(imView)
            }
            holder.cell_cardView.addView(linearlayout)

        }
    }

}
class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var cell_userCommit=itemView.findViewById<TextView>(R.id.cell_userCommit)
    var cell_userName=itemView.findViewById<TextView>(R.id.cell_userName)
    var cell_cardView=itemView.findViewById<CardView>(R.id.cell_cardView)
    var cell_like=itemView.findViewById<ImageView>(R.id.ImageFilterView_likeButton)
    var cell_Userimage=itemView.findViewById<ImageView>(R.id.cell_Userimage)

}