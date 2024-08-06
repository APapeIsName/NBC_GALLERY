package com.android.nbc_gallery.presentation.storage.ui.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.android.nbc_gallery.databinding.LayoutItemRecyclerImageBinding

class StorageViewHolder(private val binding: LayoutItemRecyclerImageBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {
        binding.ivRecyclerImageLike.isVisible = true
    }
}