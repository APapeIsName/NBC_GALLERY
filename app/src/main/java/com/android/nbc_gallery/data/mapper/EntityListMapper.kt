package com.android.nbc_gallery.data.mapper

import com.android.nbc_gallery.data.database.dto.Document
import com.android.nbc_gallery.data.entity.DataModel
import java.text.SimpleDateFormat

fun List<Document?>.toGalleryEntity() : List<DataModel.GalleryEntity> {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return map { element ->
        DataModel.GalleryEntity(
            imgUrl = element?.thumbnailUrl,
            siteName = element?.displaySitename,
            dateTime = element?.datetime?.let { simpleDateFormat.format(it) },
        )
    }
}