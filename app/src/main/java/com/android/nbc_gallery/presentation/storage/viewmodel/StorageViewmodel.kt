package com.android.nbc_gallery.presentation.storage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.nbc_gallery.data.database.APIDataStorage
import com.android.nbc_gallery.data.database.StorageData
import com.android.nbc_gallery.data.entity.DataModel
import com.android.nbc_gallery.presentation.mapper.toGalleryEntity
import com.android.nbc_gallery.presentation.repository.UiRepository
import com.android.nbc_gallery.presentation.uimodel.UiModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class StorageViewmodel(private val uiRepository: UiRepository) : ViewModel() {
    private val _liveData = MutableLiveData<List<UiModel>>()
    val liveData: LiveData<List<UiModel>> get() = _liveData

    fun updateList(list: List<UiModel>) {
        _liveData.value = list
    }

    fun updateData() {
        _liveData.value = uiRepository.getUiModelList() as List<UiModel.GalleryModel>
    }

    fun switchFavorite(index: Int) {
        _liveData.value = liveData.value?.filterIsInstance<UiModel.GalleryModel>()
            ?.mapIndexed { i, ele ->
            if(i == index) {
                if(ele.isFavorite) StorageData.deleteDataInLocal(listOf(ele).toGalleryEntity()[0])
                ele.copy(
                    isFavorite = !ele.isFavorite
                )
            } else {
                ele.copy()
            }
        }
    }

    fun findCorrectId(correctId: String): Int? {
        val index = liveData.value?.indexOf(liveData.value?.find { it.id == correctId })
        return if (index == -1) null else index
    }

    fun getFavoriteElements() : List<UiModel.GalleryModel> {
        return liveData.value?.filterIsInstance<UiModel.GalleryModel>()?.filter { it.isFavorite } ?: listOf()
    }

    fun addLoading() {
        val tempList: MutableList<UiModel> = mutableListOf()
        liveData.value?.let { tempList.addAll(it) }
        tempList.add(UiModel.LoadingModel())
        _liveData.value = tempList
    }
}

class StorageViewModelFactory(val uiRepository: UiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return StorageViewmodel(uiRepository) as T
    }
}


