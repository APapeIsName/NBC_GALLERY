package com.android.nbc_gallery.data.database.dto.video


import com.google.gson.annotations.SerializedName

data class VideoDTO(
    @SerializedName("documents")
    val documents: List<Document?>?,
    @SerializedName("meta")
    val meta: Meta?
)