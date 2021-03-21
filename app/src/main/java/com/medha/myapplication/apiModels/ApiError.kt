package com.medha.myapplication.apiModels

import com.google.gson.annotations.SerializedName

class ApiError {

    @SerializedName("success")
    var success:Boolean = true

    @SerializedName("error")
    var errorMessage:String = ""

    var message:String = ""
    var statusCode:Int = 0
    var data:Any? = null
}