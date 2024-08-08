package com.android.nbc_gallery.presentation.mapper

import com.android.nbc_gallery.data.entity.DataModel
import com.android.nbc_gallery.presentation.uimodel.UiModel
import java.text.SimpleDateFormat

@JvmName("dataToUi")
fun List<DataModel.GalleryEntity>.toGalleryModel() : List<UiModel.GalleryModel> {
    return map { element ->
        UiModel.GalleryModel(
            id = element.id,
            type = element.type,
            imgUrl = element.imgUrl,
            title = element.title,
            dateTime = element.dateTime,
            isFavorite = element.isFavorite,
        )
    }
}

@JvmName("uiToData")
fun List<UiModel.GalleryModel>.toGalleryEntity() : List<DataModel.GalleryEntity> {
    return map { element ->
        DataModel.GalleryEntity(
            id = element.id,
            type = "Video",
            imgUrl = element.imgUrl,
            title = element.title,
            dateTime = element.dateTime,
            isFavorite = element.isFavorite,
        )
    }
}