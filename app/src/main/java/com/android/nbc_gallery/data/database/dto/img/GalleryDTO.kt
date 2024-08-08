package com.android.nbc_gallery.data.database.dto.img


import com.google.gson.annotations.SerializedName

data class GalleryDTO(
    @SerializedName("documents")
    val documents: List<Document?>?,
    @SerializedName("meta")
    val meta: Meta?
)