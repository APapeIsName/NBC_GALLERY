package com.android.nbc_gallery.data.database.dto.video


import com.google.gson.annotations.SerializedName
import java.util.Date

data class Document(
    @SerializedName("author")
    val author: String?,
    @SerializedName("datetime")
    val datetime: Date?,
    @SerializedName("play_time")
    val playTime: Int?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?
)