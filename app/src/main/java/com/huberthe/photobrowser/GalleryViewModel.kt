package com.huberthe.photobrowser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huberthe.photobrowser.data.GalleryRepository
import com.huberthe.photobrowser.data.entities.Photo
import io.reactivex.disposables.Disposable

class GalleryViewModel(private val repository: GalleryRepository) : ViewModel() {

    private var picWidth: Int = 0
    private var picHeight: Int = 0

    private val photos: MutableLiveData<ArrayList<Photo>> = MutableLiveData()

    private val disposables = HashSet<Disposable>()

    fun initPhotoSize(width: Int, height: Int) {
        this.picWidth = width
        this.picHeight = height
    }

    fun loadPhoto(page: Int) {
        disposables.add(
            repository.retrievePhotos(picWidth, picHeight, page, PAGE_LIMIT)
                .subscribe({
                    mergePhotos(it)
                }, {
                    // TODO: display error
                })
        )
    }

    fun photos(): LiveData<ArrayList<Photo>> = photos

    private fun mergePhotos(list: ArrayList<Photo>) {
        var currentList = photos.value
        if (currentList == null) {
            currentList = list
        } else {
            currentList.addAll(list)
        }
        photos.postValue(currentList)
    }


    override fun onCleared() {
        super.onCleared()
        for (disposable in disposables) {
            disposable.dispose()
        }
    }


    companion object {

        private const val PAGE_LIMIT = 50
    }
}