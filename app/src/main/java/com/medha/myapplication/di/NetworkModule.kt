package com.medha.myapplication.di

import com.medha.myapplication.BuildConfig
import com.medha.myapplication.MyApplication
import com.medha.myapplication.api.ApiService
import com.medha.myapplication.api.SuspendService
import com.medha.myapplication.utilities.Constants
import com.medha.myapplication.utilities.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    var sessionValidator: MyApplication.InvalidateUnAuthorizedSession? = null

    @Provides
    @Singleton
    fun retrofit(): Retrofit = Retrofit.Builder()
        .client(shiprocketClient())
        .baseUrl("https://www.google.com")
        .addConverterFactory(GsonConverterFactory.create())
        //.addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()


    @Provides
    @Singleton
    fun retrofitConnectionCloseInterceptor(): Retrofit = Retrofit.Builder()
        .client(shiprocketClientConnectionCloseInterceptor())
        .baseUrl("https://www.google.com")
        .addConverterFactory(GsonConverterFactory.create())
        //.addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun shiprocketClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .apply {
                this.addNetworkInterceptor(interceptor())
                this.addInterceptor(headerAuthorizationInterceptor())
            }
            .build()
    }

    @Provides
    @Singleton
    fun shiprocketClientConnectionCloseInterceptor(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .apply {
                this.addNetworkInterceptor(interceptor())
                this.addInterceptor(headerAuthorizationInterceptorConnectionClose())
            }
            .build()
    }

    @Provides
    @Singleton
    fun headerAuthorizationInterceptorConnectionClose(): Interceptor {
        return Interceptor { chain ->
            var request: Request = chain.request()
            val headers = request.headers().newBuilder()
                .add(
                    "Authorization",
                    "Bearer ${SharedPreferencesHelper.defaultPrefs().getString(Constants.SharedPrefs.USER_TOKEN, "")}"
                )
                .add("Content-Type", "application/json")
                .add("Connection", "close")
                .add("X-os", "android")
                .add("X-app-version", BuildConfig.VERSION_NAME)
                .add("user-agent", "Android")
                .build()
            request = request.newBuilder().headers(headers).build()
            val proceed: Response = chain.proceed(request)
            if (proceed.code() == 401 || proceed.code() == 401) {
                sessionValidator?.invalidate()
            }
            proceed

        }
    }

    @Provides
    @Singleton
    fun interceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG)
                this.level = HttpLoggingInterceptor.Level.BODY
            else
                this.level = HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun headerAuthorizationInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request: Request = chain.request()
            val headers = request.headers().newBuilder()
                .add(
                    "Authorization",
                    "Bearer ${SharedPreferencesHelper.defaultPrefs().getString(Constants.SharedPrefs.USER_TOKEN, "")}"
                )
                .add("Content-Type", "application/json")
                .add("X-os", "android")
                .add("X-app-version", BuildConfig.VERSION_NAME)
                .add("user-agent", "Android")
                .build()
            request = request.newBuilder().headers(headers).build()
            val proceed: Response = chain.proceed(request)
            if (proceed.code() == 401 || proceed.code() == 401) {
                sessionValidator?.invalidate()
            }
            proceed
        }
    }

    @Provides
    fun shiprocketService(): ApiService {
        return retrofitConnectionCloseInterceptor().create(ApiService::class.java)
    }

    @Provides
    fun suspendService(): SuspendService {
        return retrofitConnectionCloseInterceptor().create(SuspendService::class.java)
    }
}