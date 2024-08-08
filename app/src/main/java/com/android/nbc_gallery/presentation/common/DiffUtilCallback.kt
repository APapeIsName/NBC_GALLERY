package com.android.nbc_gallery.presentation.common

import androidx.recyclerview.widget.DiffUtil
import com.android.nbc_gallery.presentation.uimodel.UiModel

class GalleryDiffUtilCallback: DiffUtil.ItemCallback<UiModel>() {
    override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        return oldItem == newItem
    }
}