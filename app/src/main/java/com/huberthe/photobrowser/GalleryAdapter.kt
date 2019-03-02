package com.huberthe.photobrowser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.huberthe.photobrowser.data.entities.Photo
import com.squareup.picasso.Picasso

class GalleryAdapter(private val screenWidth: Int) : RecyclerView.Adapter<GalleryAdapter.GalleryItemViewHolder>() {

    private val photos = ArrayList<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryItemViewHolder {
        return GalleryItemViewHolder(
            screenWidth,
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_gallery_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: GalleryItemViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    fun onUpdated(photos: ArrayList<Photo>) {
        this.photos.clear()
        this.photos.addAll(photos)
        notifyDataSetChanged()
    }

    class GalleryItemViewHolder(private val screenWidth: Int, view: View) : RecyclerView.ViewHolder(view) {
        fun bind(photo: Photo) {
            Picasso.get().load(photo.userImageURL).resize(screenWidth, screenWidth)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(photoIv)
            userNameIv.text = photo.user
            likesIv.text = itemView.context.resources.getQuantityString(
                R.plurals.rv_gallery_item_likes_text,
                photo.likes,
                photo.likes
            )
        }

        private val photoIv: ImageView = view.findViewById(R.id.rv_gallery_item_iv)
        private val userNameIv: TextView = view.findViewById(R.id.rv_gallery_item_name_tv)
        private val likesIv: TextView = view.findViewById(R.id.rv_gallery_item_likes_tv)
    }

    data class Item<T>(val data: T, val type: Int)
}