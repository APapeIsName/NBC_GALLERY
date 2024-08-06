package com.android.nbc_gallery.presentation.mapper

import com.android.nbc_gallery.data.database.dto.Document
import com.android.nbc_gallery.presentation.uimodel.UiModel
import java.net.HttpURLConnection
import java.net.URL

fun List<Document?>.toGalleryModel() : List<UiModel.GalleryModel> {
    return map { element ->
        UiModel.GalleryModel(
            imgUrl = element?.thumbnailUrl,
            siteName = element?.displaySitename,
            dateTime = element?.datetime,
        )
    }
}