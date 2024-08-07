package com.android.nbc_gallery.data.repository

import com.android.nbc_gallery.data.database.APIDataStorage
import com.android.nbc_gallery.data.mapper.toGalleryEntity
import com.android.nbc_gallery.presentation.mapper.toGalleryModel
import com.android.nbc_gallery.presentation.repository.UiRepository
import com.android.nbc_gallery.presentation.uimodel.UiModel

class UiRepositoryGalleryImpl: UiRepository {
    override fun getUiModelList(): List<UiModel> = APIDataStorage.getApiData().toGalleryModel()
}