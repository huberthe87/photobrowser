package com.huberthe.photobrowser

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

abstract class RetrofitService {

    companion object {

        @Volatile
        private var INSTANCE: RetrofitService? = null

        @JvmStatic
        fun retrofit(): Retrofit {
            return INSTANCE?.retrofit
                ?: throw IllegalAccessException("Please call RetrofitService.setInstance before this.")
        }

        @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
        fun setInstance(instance: RetrofitService) {
            INSTANCE = instance
        }
    }

    private val retrofit: Retrofit

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor {
            Log.d("TEST", it)
        }
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        retrofit = Retrofit
            .Builder()
            .client(OkHttpClient.Builder()
                .hostnameVerifier { _, _ -> true }
                .addInterceptor(httpLoggingInterceptor)
                .protocols(Arrays.asList(Protocol.HTTP_1_1, Protocol.HTTP_2)).build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(this.getHost())
            .build()
    }

    abstract fun getHost(): String

}