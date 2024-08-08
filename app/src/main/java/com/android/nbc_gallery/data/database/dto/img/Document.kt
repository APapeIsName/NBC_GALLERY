package com.android.nbc_gallery.data.database.dto.img


import com.google.gson.annotations.SerializedName
import java.util.Date

data class Document(
    @SerializedName("collection")
    val collection: String?,
    @SerializedName("datetime")
    val datetime: Date?,
    @SerializedName("display_sitename")
    val displaySitename: String?,
    @SerializedName("doc_url")
    val docUrl: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerializedName("width")
    val width: Int?
)