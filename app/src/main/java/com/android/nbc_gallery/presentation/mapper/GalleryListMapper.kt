package com.android.nbc_gallery.presentation.mapper

import com.android.nbc_gallery.data.database.dto.Document
import com.android.nbc_gallery.presentation.uimodel.UiModel
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat

fun List<Document?>.toGalleryModel() : List<UiModel.GalleryModel> {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return map { element ->
        UiModel.GalleryModel(
            imgUrl = element?.thumbnailUrl,
            siteName = element?.displaySitename,
            dateTime = element?.datetime?.let { simpleDateFormat.format(it) },
        )
    }
}