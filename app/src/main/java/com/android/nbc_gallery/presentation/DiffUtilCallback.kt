package com.android.nbc_gallery.presentation

import androidx.recyclerview.widget.DiffUtil
import com.android.nbc_gallery.presentation.uimodel.UiModel

class GalleryDiffUtilCallback: DiffUtil.ItemCallback<UiModel.GalleryModel>() {
    override fun areItemsTheSame(oldItem: UiModel.GalleryModel, newItem: UiModel.GalleryModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UiModel.GalleryModel, newItem: UiModel.GalleryModel): Boolean {
        return oldItem == newItem
    }
}