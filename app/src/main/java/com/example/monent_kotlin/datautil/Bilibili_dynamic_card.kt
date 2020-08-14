package com.example.monent_kotlin.datautil

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class Bilibili_dynamic_card {

    /**
     * item : {"id":86800841,"title":"","description":"打扰了[2233娘_无言]有缘再见哈","category":"daily","role":[],"source":[],"pictures":[{"img_src":"https://i0.hdslb.com/bfs/album/719e8095a3cab9ae55ff08e4fc5cefc57034b9e4.jpg","img_width":1440,"img_height":3120,"img_size":892}],"pictures_count":1,"upload_time":1597302599,"at_control":"","reply":6,"settings":{"copy_forbidden":0},"is_fav":0}
     * user : {"uid":797614,"head_url":"https://i0.hdslb.com/bfs/face/547ca1ed0918d2c9fb6c2b895b3dc7209ba9ca86.jpg","name":"阿赫official","vip":{"vipType":2,"vipDueDate":1616169600000,"dueRemark":"","accessStatus":0,"vipStatus":1,"vipStatusWarn":"","themeType":0,"label":{"path":""}}}
     */
    var desc: String? = ""
    var dynamic: String? = ""
    var pic: String? = ""
    private var item: ItemBean? = null
    private var origin: String? = ""
    private var user: UserBean? = null
    private var vest: VestBean? = null
    private var sketch: SketchBean?=null

    fun getOrigin():String?{
        return origin
    }
    fun getVest():VestBean?{
        return vest
    }
    fun getItem(): ItemBean? {
        return item
    }
    fun getSketch():SketchBean?{
        return sketch
    }

    fun setItem(item: ItemBean?) {
        this.item = item
    }

    fun getUser(): UserBean? {
        return user
    }

    fun setUser(user: UserBean?) {
        this.user = user
    }

    class SketchBean{
        var cover_url:String?=""
    }

    class VestBean{
        var uid:String?=""
        var content:String?=""
        var ctrl:String?=""
    }

    class ItemBean {
        /**
         * id : 86800841
         * title :
         * description : 打扰了[2233娘_无言]有缘再见哈
         * category : daily
         * role : []
         * source : []
         * pictures : [{"img_src":"https://i0.hdslb.com/bfs/album/719e8095a3cab9ae55ff08e4fc5cefc57034b9e4.jpg","img_width":1440,"img_height":3120,"img_size":892}]
         * pictures_count : 1
         * upload_time : 1597302599
         * at_control :
         * reply : 6
         * settings : {"copy_forbidden":0}
         * is_fav : 0
         */
        var id = 0
        var title: String? = null
        var description: String? = null
        var content: String? = null
        var category: String? = null
        var pictures_count = ""
        var upload_time = ""
        var at_control: String? = null
        var reply = ""
        var settings: SettingsBean? = null
        var is_fav = ""
        var role: List<*>? = null
        var source: List<*>? = null
        var pic:String?=null
        var pictures: List<PicturesBean>? = null

        class SettingsBean {
            /**
             * copy_forbidden : 0
             */
            var copy_forbidden = ""

        }
        @Parcelize
        class PicturesBean : Parcelable {
            /**
             * img_src : https://i0.hdslb.com/bfs/album/719e8095a3cab9ae55ff08e4fc5cefc57034b9e4.jpg
             * img_width : 1440
             * img_height : 3120
             * img_size : 892
             */
            var img_src: String? = null
            var img_width = 0
            var img_height = 0
            var img_size = 0

        }
    }

    class UserBean {
        /**
         * uid : 797614
         * head_url : https://i0.hdslb.com/bfs/face/547ca1ed0918d2c9fb6c2b895b3dc7209ba9ca86.jpg
         * name : 阿赫official
         * vip : {"vipType":2,"vipDueDate":1616169600000,"dueRemark":"","accessStatus":0,"vipStatus":1,"vipStatusWarn":"","themeType":0,"label":{"path":""}}
         */
        var uid = ""
        var head_url: String? = null
        var name: String? = null
        var vip: VipBean? = null

        class VipBean {
            /**
             * vipType : 2
             * vipDueDate : 1616169600000
             * dueRemark :
             * accessStatus : 0
             * vipStatus : 1
             * vipStatusWarn :
             * themeType : 0
             * label : {"path":""}
             */
            var vipType = ""
            var vipDueDate= ""
            var dueRemark: String? = null
            var accessStatus = ""
            var vipStatus = ""
            var vipStatusWarn: String? = null
            var themeType = ""
            var label: LabelBean? = null

            class LabelBean {
                /**
                 * path :
                 */
                var path: String? = null

            }
        }
    }
}