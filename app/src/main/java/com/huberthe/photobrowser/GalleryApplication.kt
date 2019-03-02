package com.huberthe.photobrowser

import android.app.Application

class GalleryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RetrofitService.setInstance(object : RetrofitService() {

            override fun getHost(): String = HOST

        })
    }

    companion object {
        private val HOST = "https://pixabay.com/api/"
    }
}