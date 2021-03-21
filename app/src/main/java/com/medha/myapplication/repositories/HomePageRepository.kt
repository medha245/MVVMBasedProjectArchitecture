package com.medha.myapplication.repositories

import com.medha.myapplication.api.ApiService
import com.medha.myapplication.api.NetworkCall
import com.medha.myapplication.apiModels.response.RevenueDataResponse
import okhttp3.ResponseBody
import javax.inject.Inject

class HomePageRepository @Inject constructor(val api: ApiService) {

    // pass the parser class instance
    fun getWalletBalnce(url:String) = NetworkCall<ResponseBody>(RevenueDataResponse()).makeCall(api.getRevenueData(url))
}