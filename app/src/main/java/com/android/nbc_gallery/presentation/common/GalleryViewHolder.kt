package com.android.nbc_gallery.presentation.common

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.android.nbc_gallery.databinding.LayoutItemRecyclerImageBinding
import com.android.nbc_gallery.presentation.common.GalleryRecyclerViewAdapter
import com.android.nbc_gallery.presentation.uimodel.UiModel

class GalleryViewHolder(private val binding: LayoutItemRecyclerImageBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(itemClick: GalleryRecyclerViewAdapter.ItemClick?, drawImage: GalleryRecyclerViewAdapter.DrawImage?, ui: UiModel) {
        ui as UiModel.GalleryModel
        binding.ivRecyclerImageLike.isVisible = (ui.isFavorite == true)
        drawImage?.onDraw(ui.imgUrl ?: "")?.into(binding.ivRecyclerImageContent)
        binding.tvRecyclerImageFrom.text = "[${ui.type}] ${ui.title}"
        binding.tvRecyclerImageDate.text = ui.dateTime
        binding.cvRecycler.setOnClickListener {
            itemClick?.onClick(ui.id)
        }
    }
}