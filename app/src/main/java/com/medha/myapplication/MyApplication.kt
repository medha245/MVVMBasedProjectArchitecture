package com.medha.myapplication

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication {
    interface InvalidateUnAuthorizedSession {
        fun invalidate()
    }
}