package com.android.nbc_gallery.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.nbc_gallery.data.repository.UiRepositoryGalleryImpl
import com.android.nbc_gallery.presentation.repository.UiRepository
import com.android.nbc_gallery.presentation.uimodel.UiModel

class GalleryViewmodel(private val uiRepository: UiRepository) : ViewModel() {
    private val _liveData = MutableLiveData<List<UiModel.GalleryModel>>()
    val liveData : LiveData<List<UiModel.GalleryModel>> get() = _liveData

    fun updateData() {
        _liveData.value = uiRepository.getUiModelList() as List<UiModel.GalleryModel>
    }

}

class GalleryViewModelFactory(val uiRepository: UiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return GalleryViewmodel(uiRepository) as T
    }
}