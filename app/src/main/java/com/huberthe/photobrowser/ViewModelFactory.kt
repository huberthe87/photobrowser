package com.huberthe.photobrowser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.huberthe.photobrowser.data.GalleryRepository
import com.huberthe.photobrowser.data.PhotoService
import retrofit2.Retrofit

class ViewModelFactory(retrofit: Retrofit) : ViewModelProvider.Factory {

    private val repository: GalleryRepository = GalleryRepository(retrofit.create(PhotoService::class.java))

    companion object {

        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(): ViewModelFactory {
            if (INSTANCE == null) {
                INSTANCE = ViewModelFactory(RetrofitService.retrofit())
            }
            return INSTANCE!!
        }

    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getDeclaredConstructor(GalleryRepository::class.java).newInstance(repository)
    }
}