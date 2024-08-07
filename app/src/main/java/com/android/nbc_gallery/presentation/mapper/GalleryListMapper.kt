package com.android.nbc_gallery.presentation.mapper

import com.android.nbc_gallery.data.database.dto.Document
import com.android.nbc_gallery.data.entity.DataModel
import com.android.nbc_gallery.presentation.uimodel.UiModel
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat

fun List<DataModel.GalleryEntity>.toGalleryModel() : List<UiModel.GalleryModel> {
    return map { element ->
        UiModel.GalleryModel(
            imgUrl = element.imgUrl,
            siteName = element.siteName,
            dateTime = element.dateTime,
            isFavorite = element.isFavorite,
        )
    }
}