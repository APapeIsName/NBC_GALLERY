package com.android.nbc_gallery.presentation.repository

import com.android.nbc_gallery.presentation.uimodel.UiModel

interface UiRepository {
    fun getUiModelList(): List<UiModel>
}