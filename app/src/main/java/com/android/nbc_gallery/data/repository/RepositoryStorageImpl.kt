package com.android.nbc_gallery.data.repository

import com.android.nbc_gallery.data.database.StorageData
import com.android.nbc_gallery.data.entity.DataModel
import com.android.nbc_gallery.presentation.mapper.toGalleryModel
import com.android.nbc_gallery.presentation.repository.UiRepository
import com.android.nbc_gallery.presentation.uimodel.UiModel

class UiRepositoryStorageImpl : UiRepository {
    override fun getUiModelList(): List<UiModel> = StorageData.getData().filterIsInstance<DataModel.GalleryEntity>().toGalleryModel()
}
