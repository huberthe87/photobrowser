package com.huberthe.photobrowser

import android.graphics.Point
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView

class GalleryActivity : AppCompatActivity() {

    lateinit var galleryRv: RecyclerView

    lateinit var viewModel: GalleryViewModel

    lateinit var galleryAdapter: GalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        galleryRv = findViewById(R.id.activity_gallery_rv)
        val screenWidth = getScreenWidth()
        galleryAdapter = GalleryAdapter(screenWidth)
        galleryRv.adapter = galleryAdapter
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory.getInstance()
        ).get(GalleryViewModel::class.java)
        viewModel.initPhotoSize(screenWidth, screenWidth)
        viewModel.photos().observe(this, Observer {
            galleryAdapter.onUpdated(it)
        })
        viewModel.loadPhoto(1)
    }

    private fun getScreenWidth(): Int {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }
}