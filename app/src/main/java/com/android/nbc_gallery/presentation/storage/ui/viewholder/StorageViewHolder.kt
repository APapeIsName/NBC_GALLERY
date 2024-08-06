package com.android.nbc_gallery.presentation.storage.ui.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.android.nbc_gallery.databinding.LayoutItemRecyclerImageBinding
import com.android.nbc_gallery.presentation.GalleryRecyclerViewAdapter
import com.android.nbc_gallery.presentation.uimodel.UiModel

class StorageViewHolder(private val binding: LayoutItemRecyclerImageBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(itemClick: GalleryRecyclerViewAdapter.ItemClick?, drawImage: GalleryRecyclerViewAdapter.DrawImage?, ui: UiModel.GalleryModel) {
        binding.ivRecyclerImageLike.isVisible = (ui.isFavorite == true)
        drawImage?.onDraw(ui.imgUrl ?: "")?.into(binding.ivRecyclerImageContent)
        binding.tvRecyclerImageFrom.text = ui.siteName
        binding.tvRecyclerImageDate.text = ui.dateTime
        binding.cvRecycler.setOnClickListener {
            itemClick?.onClick(ui.id)
        }
    }
}