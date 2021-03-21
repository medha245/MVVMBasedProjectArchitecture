package com.medha.myapplication

import android.app.Application
import com.medha.myapplication.utilities.SharedPreferencesHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application(){
    interface InvalidateUnAuthorizedSession {
        fun invalidate()
    }

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesHelper.context = this
    }


}