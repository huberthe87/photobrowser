package com.huberthe.photobrowser.data

import com.google.gson.Gson
import com.huberthe.photobrowser.data.entities.Photo
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class GalleryRepository(private val photoService: PhotoService) {

    private val gsonInstance: Gson = Gson()

    /**
     * Retrieve photos by page
     *
     */
    fun retrievePhotos(
        width: Int,
        height: Int,
        page: Int = 1,
        limit: Int = 50
    ): Observable<ArrayList<Photo>> =
        photoService.getPhotos(width, height, page, limit).map {
            val jsonObject = it.asJsonObject
            val photos = ArrayList<Photo>()
            val hitsRaw = jsonObject.getAsJsonArray("hits")
            for (hit in hitsRaw) {
                photos.add(gsonInstance.fromJson(hit, Photo::class.java))
            }
            photos
        }.subscribeOn(Schedulers.io())
}