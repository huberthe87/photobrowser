package com.huberthe.photobrowser.data.entities

import com.google.gson.annotations.SerializedName

data class Photo(
    val userImageURL: String,
    @SerializedName("user")
    val driver: String,
    val likes: Int,
    val id: Int
)
