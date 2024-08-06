package com.android.nbc_gallery.presentation

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.android.nbc_gallery.databinding.LayoutItemRecyclerImageBinding
import com.android.nbc_gallery.presentation.search.ui.viewholder.SearchViewHolder
import com.android.nbc_gallery.presentation.storage.ui.viewholder.StorageViewHolder
import com.android.nbc_gallery.presentation.uimodel.UiModel
import com.bumptech.glide.RequestBuilder

// type 0 : search, 1 : storage
class GalleryRecyclerViewAdapter(private val type: Int) : ListAdapter<UiModel.GalleryModel, RecyclerView.ViewHolder>(
    GalleryDiffUtilCallback()
) {
    var itemClick : ItemClick? = null
    var drawImage : DrawImage? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ViewBinding
        if(viewType == 0) {
            binding = LayoutItemRecyclerImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SearchViewHolder(binding)
        } else {
            binding = LayoutItemRecyclerImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return StorageViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val aa = getItem(position)
        if(holder is SearchViewHolder) holder.bind(itemClick, drawImage, getItem(position))
        else if(holder is StorageViewHolder) holder.bind()
    }

    override fun getItemViewType(position: Int): Int {
        return type
    }

    fun interface ItemClick {
        fun onClick()
    }

    fun interface DrawImage {
        fun onDraw(url: String): RequestBuilder<Drawable>
    }

}