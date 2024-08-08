package com.android.nbc_gallery.data.mapper

import com.android.nbc_gallery.data.entity.DataModel
import java.text.SimpleDateFormat

@JvmName("imgDocument")
fun List<com.android.nbc_gallery.data.database.dto.img.Document?>.toGalleryEntity() : List<DataModel.GalleryEntity> {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return map { element ->
        DataModel.GalleryEntity(
            type = "Image",
            imgUrl = element?.thumbnailUrl,
            title = element?.displaySitename,
            dateTime = element?.datetime?.let { simpleDateFormat.format(it) },
        )
    }
}

@JvmName("videoDocumentTo")
fun List<com.android.nbc_gallery.data.database.dto.video.Document?>.toGalleryEntity() : List<DataModel.GalleryEntity> {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return map { element ->
        DataModel.GalleryEntity(
            type = "Video",
            imgUrl = element?.thumbnail,
            title = element?.title,
            dateTime = element?.datetime?.let { simpleDateFormat.format(it) },
        )
    }
}