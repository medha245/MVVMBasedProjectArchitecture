package com.medha.myapplication.api

import com.medha.myapplication.apiModels.ApiError
import retrofit2.Call

open class Resource<T> private constructor(val status: Status, val data: T?, val apiError:ApiError?,
                                           val call: Call<T>?, val parsedData:Any?) {
    enum class Status {
        SUCCESS, ERROR,FAILURE, LOADING, PROGRESS_UPDATE
    }
    companion object {
        fun <T> success(data: T?, call: Call<T>, parsedData : Any?): Resource<T> {
            return Resource(Status.SUCCESS, data, null, call, parsedData)
        }
        fun <T> error(apiError: ApiError?, call: Call<T>, parsedData : Any?): Resource<T> {
            return Resource(Status.ERROR, null, apiError, call,parsedData = null)
        }
        fun <T> loading(data: T?, call: Call<T>, parsedData : Any?): Resource<T> {
            return Resource(Status.LOADING, data, null, call,parsedData = null)
        }
        fun <T> progressUpdate(data: T?, call: Call<T>, parsedData : Any?, percentage:Int): Resource<T> {
            return Resource(Status.LOADING, data, null, call,parsedData = null)
        }
        fun <T> failure(apiFailure:ApiError?, call: Call<T>, parsedData : Any?): Resource<T> {
            return Resource(Status.FAILURE,null,apiFailure, call,parsedData = null)
        }
    }
}