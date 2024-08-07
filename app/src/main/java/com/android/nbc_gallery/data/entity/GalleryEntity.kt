package com.android.nbc_gallery.data.entity

sealed class DataModel{
    data class GalleryEntity(
        val imgUrl: String? = null,
        val siteName: String? = null,
        val dateTime: String? = null,
        val isFavorite: Boolean = false,
    ) : DataModel()
}
