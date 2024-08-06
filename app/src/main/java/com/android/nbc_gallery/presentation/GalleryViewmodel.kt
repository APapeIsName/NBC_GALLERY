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
    val liveData: LiveData<List<UiModel.GalleryModel>> get() = _liveData

    fun updateData() {
        _liveData.value = uiRepository.getUiModelList() as List<UiModel.GalleryModel>
    }

    fun switchFavorite(index: Int) {
        _liveData.value = liveData.value?.mapIndexed { i, ele ->
            if(i == index) ele.copy(
                isFavorite = !ele.isFavorite
            ) else {
                ele.copy()
            }
        }
    }

    fun findCorrectId(correctId: String): Int? {
        val index = liveData.value?.indexOf(liveData.value?.find { it.id == correctId })
        return if (index == -1) null else index
    }

    fun getFavoriteElements() : List<UiModel.GalleryModel> {
        return liveData.value?.filter { it.isFavorite } ?: listOf()
    }
}
class GalleryViewModelFactory(val uiRepository: UiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return GalleryViewmodel(uiRepository) as T
    }
}