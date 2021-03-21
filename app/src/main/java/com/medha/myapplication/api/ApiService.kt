package com.medha.myapplication.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET
    fun getRevenueData(
        @Url url: String?
    ): Call<ResponseBody>

}