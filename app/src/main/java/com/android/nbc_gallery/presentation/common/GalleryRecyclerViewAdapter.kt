package com.android.nbc_gallery.presentation.common

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.android.nbc_gallery.databinding.LayoutItemRecyclerImageBinding
import com.android.nbc_gallery.databinding.LayoutItemRecyclerLoadingBinding
import com.android.nbc_gallery.presentation.search.ui.viewholder.SearchViewHolder
import com.android.nbc_gallery.presentation.storage.ui.viewholder.StorageViewHolder
import com.android.nbc_gallery.presentation.uimodel.UiModel
import com.bumptech.glide.RequestBuilder

// type 0 : search, 1 : storage
enum class ItemType(val type: Int) {
    GALLERY(0), LOADING(1),
}

class GalleryRecyclerViewAdapter : ListAdapter<UiModel, RecyclerView.ViewHolder>(
    GalleryDiffUtilCallback()
) {
    var itemClick : ItemClick? = null
    var drawImage : DrawImage? = null
    var currentListChange: CurrentListChange? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ViewBinding
        lateinit var viewHolder: RecyclerView.ViewHolder
        when(viewType) {
            ItemType.GALLERY.type -> {
                binding = LayoutItemRecyclerImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                viewHolder = GalleryViewHolder(binding)
            }
            ItemType.LOADING.type -> {
                binding = LayoutItemRecyclerLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                viewHolder = LoadingViewHolder(binding)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is GalleryViewHolder -> holder.bind(itemClick, drawImage, currentList[position])
            is LoadingViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int =
        when(currentList[position]) {
            is UiModel.GalleryModel -> ItemType.GALLERY.type
            is UiModel.LoadingModel -> ItemType.LOADING.type
        }

    fun interface ItemClick {
        fun onClick(id: String)
    }

    fun interface DrawImage {
        fun onDraw(url: String): RequestBuilder<Drawable>
    }

    fun interface CurrentListChange {
        fun onChange(last: Int)
    }

    override fun onCurrentListChanged(
        previousList: MutableList<UiModel>,
        currentList: MutableList<UiModel>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        currentListChange?.onChange(previousList.lastIndex)
    }

}