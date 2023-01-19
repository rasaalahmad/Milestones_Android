package com.example.milestone2.data_classes

data class Meme(var id:String?, var name:String?, var url:String?, var width:Int?, var height:Int?, var boxCount:Int?) {
    constructor() : this(null,null,null,0,0,0)
}
