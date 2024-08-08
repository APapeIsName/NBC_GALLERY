package com.android.nbc_gallery.data.entity

import java.util.UUID

sealed class DataModel{
    abstract val id: String
    data class GalleryEntity(
        override val id: String = UUID.randomUUID().toString(),
        val type: String? = null,
        val imgUrl: String? = null,
        val title: String? = null,
        val dateTime: String? = null,
        val isFavorite: Boolean = false,
    ) : DataModel()
}
