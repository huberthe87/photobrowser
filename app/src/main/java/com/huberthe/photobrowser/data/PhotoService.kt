package com.huberthe.photobrowser.data

import com.google.gson.JsonElement
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface PhotoService {

    @GET("https://pixabay.com/api/")
    fun getPhotos(
        @Query("width") width: Int,
        @Query("height") height: Int,
        @Query("page") page: Int,
        @Query("per_page") limit: Int,
        @Query("key") key: String = "8630898-e092bf16cb1dd9ff6a483dabf"
    ): Observable<JsonElement>
}