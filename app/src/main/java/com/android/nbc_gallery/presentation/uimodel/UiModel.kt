package com.android.nbc_gallery.presentation.uimodel

import android.net.Uri
import java.text.SimpleDateFormat
import java.util.UUID

sealed class UiModel{
    abstract val id: String
    data class GalleryModel(
        override val id: String = UUID.randomUUID().toString(),
        val imgUrl: String? = null,
        val siteName: String? = null,
        val dateTime: String? = null,
        val isFavorite: Boolean? = null,
    ): UiModel()
}

