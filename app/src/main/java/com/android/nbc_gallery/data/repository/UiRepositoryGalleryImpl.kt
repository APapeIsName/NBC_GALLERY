package com.android.nbc_gallery.data.repository

import android.util.Log
import com.android.nbc_gallery.data.database.APIDataStorage
import com.android.nbc_gallery.data.database.StorageData
import com.android.nbc_gallery.data.entity.DataModel
import com.android.nbc_gallery.data.mapper.toGalleryEntity
import com.android.nbc_gallery.presentation.mapper.toGalleryModel
import com.android.nbc_gallery.presentation.repository.UiRepository
import com.android.nbc_gallery.presentation.uimodel.UiModel

class UiRepositoryGalleryImpl: UiRepository {
    override fun getUiModelList(): List<UiModel> = APIDataStorage.getApiData().addFavorite().toGalleryModel()
}

fun List<DataModel.GalleryEntity>.addFavorite() : List<DataModel.GalleryEntity> {
    val datas = (StorageData.getData() as List<DataModel.GalleryEntity>)
    val list : MutableList<DataModel.GalleryEntity> = this.toMutableList()
    for(i in list.indices) {
        for(j in datas.indices) {
            if(list[i].imgUrl == datas[j].imgUrl)
                list[i] = datas[j].copy(isFavorite = true)
        }
    }
    Log.d("불러오기;",list.toString())
    return list
}