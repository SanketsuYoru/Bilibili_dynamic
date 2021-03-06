package com.example.monent_kotlin.datautil

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getDrawable
import com.android.volley.NetworkResponse
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.monent_kotlin.R
import com.google.gson.Gson
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


object loadDataUtil{
    lateinit var request:RequestQueue

    fun getType(str:String):String
    {
        return when(str){
            "2" -> "normal_commit_withpic"
            "1"->"repost"
            "8"->"video"
            "4"->"normal_commit_withoutpic"
            "2048"->"topic"
            "64"->"article"
            "512"->"bangumi"
            else -> "others"
        }
    }

    fun getContent(item:Bilibili_dynamic.DataBean.CardsBean):ContentBlock{
        var contentBlock:ContentBlock?

        var card=Gson().fromJson(item.card,Bilibili_dynamic_card::class.java)
        var pics=card?.getItem()?.pictures?.toMutableList()?:ArrayList()
        var content=""
        var username=item.desc?.user_profile?.info?.uname?:""
        var usericon=item.desc?.user_profile?.info?.face?:""
        contentBlock=ContentBlock("","","",ArrayList(),"")

        //todo when sentences to deal the diffident type of the card
        var type=""
        if (item.desc?.type!=null)
            type=getType(item.desc?.type!!)
        when(type){
            "bangumi"->{
                content=card.getApiSeasonInfo()?.title?:""
                var temp=Bilibili_dynamic_card.ItemBean.PicturesBean()
                temp.img_src=card.getApiSeasonInfo()?.cover
                usericon=card.getApiSeasonInfo()?.cover?:""
                pics.add(temp)


            }
            "article"->{
                content="title : "+card.title+"\n summary : "+card.summary
                for(pic in card?.image_urls?.toMutableList()?:ArrayList())
                {
                    var temp=Bilibili_dynamic_card.ItemBean.PicturesBean()
                    temp.img_src=pic
                    pics.add(temp)
                }

            }
            "repost"->{
                var card_origin=Gson().fromJson(card.getOrigin(),Bilibili_dynamic_card::class.java)
                if(card_origin.getItem()!=null)// if the origin is pic commit or cast
                {
                    //pic commit
                    content=card.getItem()?.content+" \norigin: "+card_origin.getItem()?.description
                    pics=card_origin?.getItem()?.pictures?.toMutableList()?:ArrayList()
                    type="repost_pic_commit"
                }

                else
                {

                    content=card.getItem()?.content+" \norigin: "+card_origin.dynamic
                    if(card_origin.roomid!="")//if the origin is cast
                        {
                            var temp=Bilibili_dynamic_card.ItemBean.PicturesBean()
                            temp.img_src=card_origin?.cover
                            pics.add(temp)
                            type="cast"
                        }
                    else//if the origin is video
                    {
                        var temp=Bilibili_dynamic_card.ItemBean.PicturesBean()
                        temp.img_src=card_origin?.pic
                        pics.add(temp)
                        type="video"
                    }

                }
            }
            "normal_commit_withpic"->{
                content=card.getItem()?.description?:""
            }
            "normal_commit_withoutpic"->{
                content=card.getItem()?.content?:""
            }
            "topic"->{
                content=card.getVest()?.content?:""
                var temp=Bilibili_dynamic_card.ItemBean.PicturesBean()
                temp.img_src=card.getSketch()?.cover_url
                pics.add(temp)
            }
            "video"->{
                content=card.dynamic?:""
                var temp=Bilibili_dynamic_card.ItemBean.PicturesBean()
                temp.img_src=card?.pic
                pics.add(temp)
            }
            else->{
                //todo other type
            }
        }
        contentBlock=ContentBlock(usericon,username,content,pics,type)
        contentBlock.setLike_Unlike((item.desc?.is_liked=="1"))
        return contentBlock

    }


    fun dataParsing(uid: String,viewModel: DataViewModel){
        var requestUrl="https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/dynamic_new?uid=${uid}&type_list=268435455"
        var response_list: MutableList<ContentBlock> = ArrayList()
        val stringRequest =object :StringRequest(
            Method.GET,
            requestUrl,
            Response.Listener {
                var response=Gson().fromJson(it,Bilibili_dynamic::class.java)
                var cards=response.getData()?.cards
                if (cards != null) {
                    for (item in cards) {
                        response_list.add(getContent(item))
                    }
                }
                viewModel.setContent(response_list)
               Log.e("Response.Listener", cards?.get(0)?.card)
            },
            Response.ErrorListener {
                Log.d("Response.ErrorListener",it.toString())
            }
        ){
            override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                val parsed: String
                parsed = try {
                    String(response!!.data.toUByteArray().toByteArray(), Charset.forName("utf8"))
                } catch (e: UnsupportedEncodingException) {
                    String(response!!.data)
                }
                return Response.success(parsed,HttpHeaderParser.parseCacheHeaders(response))
            }

            override fun getHeaders(): MutableMap<String, String> {
                return mapOf(
                    "authority" to "api.vc.bilibili.com",
                    "method" to "GET",
                    "referer" to "https://www.bilibili.com/",
                    "connection" to "keep-alive",
                    "accept-encoding" to "utf-8",
                    "accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
                    "cookie" to "fts=1521024428; pgv_pvi=6015641600; im_notify_type_11743208=0; LIVE_BUVID=35570b9efe35b13b559a8f1e1111892c; LIVE_BUVID__ckMd5=b66ca168e960d1d0; buvid3=ABEFB80F-F5D5-47B4-A306-FA3283175637103054infoc; _ga=GA1.2.1195467944.1532734636; CURRENT_FNVAL=16; rpdid=|(J|~YRYm|~)0J\\'ullYum)m)k; im_seqno_11743208=572; im_local_unread_11743208=0; _uuid=DA2F6410-5450-BC16-446C-7B6EC13F49B497214infoc; hasstrong=1; stardustvideo=1; laboratory=1-1; sid=5a25fr3x; CURRENT_QUALITY=116; bp_t_offset_11743208=383996649230477383; bsource=login_download_bili; DedeUserID=11743208; DedeUserID__ckMd5=b986b5754bc6a73e; SESSDATA=a60a3fbb%2C1603858919%2Cbefcf*51; bili_jct=dd226e39641d6e2908c4153c199be020; flash_player_gray=false; html5_player_gray=false; PVID=4",
                    "accept-language" to "zh-CN,zh;q=0.9,ja-JP;q=0.8,ja;q=0.7,en-US;q=0.6,en;q=0.5",
                    "user-agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36"
                ).toMutableMap()
            }
        }
        request.add(stringRequest)
    }


    fun loadImage(url:String,context: Context,view: ImageView){
        Glide.with(context)
            .load(url)
            .placeholder(getDrawable(context,R.drawable.ic_baseline_image_24))
            .listener(object :RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    //swipe_news.isRefreshing=false
                    //TODO("stop refresh")
                    return false
                }
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    //swipe_news.isRefreshing=false
                    //TODO("stop refresh")
                    return false
                }
            })
            .into(view)
    }


    fun loadImage_dynamic(url:String?, context: Context, placeholder: GradientDrawable):ImageView{
        var imView= ImageView(context)
        imView.scaleType= ImageView.ScaleType.CENTER_CROP
        val lp = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,1.0f)
        imView.layoutParams = lp
        Glide.with(context)
            .load(url)
            .placeholder(placeholder)
            .listener(object :RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    //swipe_news.isRefreshing=false
                    //TODO("stop refresh")
                    return false
                }
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    //swipe_news.isRefreshing=false
                    //TODO("stop refresh")
                    return false
                }
            })
            .into(imView)
        return imView
    }
    fun loadVideo(){
        //todo loadVideo
    }

    fun fatchData(dataViewModel: DataViewModel){
        var requestList:MutableList<ContentBlock> = ArrayList()
        //todo replace uid to var
        loadDataUtil.dataParsing("11743208",dataViewModel)
    }

    }