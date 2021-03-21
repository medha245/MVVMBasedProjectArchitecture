package com.medha.myapplication.api

import androidx.lifecycle.MutableLiveData
import com.medha.myapplication.apiModels.ApiError
import com.medha.myapplication.apiModels.ErrorUtils
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.CoroutineContext

open class NetworkCall<T>(val any: Any? = null) {
    lateinit var call: Call<T>

    private val parentJob = SupervisorJob()
    var callBackKt: CallBackKt<T>? = null
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    /**
     * @return Live data of resource type
     * so basically instead of having two live data one for progress another for data
     * we will now have one live data to observe which will give both loading status as well as
     * data from api when loading finishes
     * Resource to read : https://proandroiddev.com/demystifying-retrofit-network-call-with-kotlin-livedata-27437439137a
     *
     * any parameter : Often times we want to specify Call<ResponseBody> but want to parse the data and return some other class
     * Extending BaseParserClass and passing the class name in Network call will serve the same purpose you can parse the data in your
     * own class on success parsedData parameter will return your own parsed format of data
     */

    fun makeCall(call: Call<T>): MutableLiveData<Resource<T>> {
        this.call = call
        this.callBackKt = CallBackKt<T>()
        callBackKt?.result?.value = Resource.loading(null, call, null)
        this.call.clone().enqueue(callBackKt)

        return callBackKt?.result!!
    }

    inner class CallBackKt<T>() : Callback<T> {
        var result: MutableLiveData<Resource<T>> = MutableLiveData()

        override fun onFailure(call: Call<T>, t: Throwable) {

            if (t is IOException) {
                val apiError = ApiError()
                apiError.errorMessage = "Please check your internet connection"
                result.value = Resource.failure(apiError, call, null)
            } else {
                val apiError = ApiError()
                apiError.errorMessage = t.message ?: "Something went wrong"
                result.value = Resource.failure(apiError, call, null)
            }

        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                if (any != null && (any is BaseParserClass)) {
                    var parsed: Any? = null
                    val job = scope.launch {

                        parsed = (any as BaseParserClass).parser(response.body())
                    }

                    runBlocking {
                        job.join()
                        result.value = Resource.success(response.body(), call, parsed)
                    }

                } else {
                    result.value = Resource.success(response.body(), call, null)
                }
            } else {
                result.value =
                    Resource.error(ErrorUtils.parseError(response.errorBody()), call, null)
            }
        }
    }

    fun cancel() {
        if (::call.isInitialized) {
            call.cancel()
        }
    }

    interface ProgressUploadListener {
        fun uploadProgress(percentage: Int)
    }
}