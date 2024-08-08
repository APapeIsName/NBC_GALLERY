package com.android.nbc_gallery.data.database

import android.util.Log
import com.android.nbc_gallery.data.database.network.RetrofitClient
import com.android.nbc_gallery.data.entity.DataModel
import com.android.nbc_gallery.data.mapper.toGalleryEntity

object APIDataStorage {
    private val dataList: MutableList<DataModel.GalleryEntity> = mutableListOf()

    suspend fun getDataFromApi(
        query: String,
        sort: String = "accuracy",
        page: Int = 1,
        size: Int = 15,
    ) {
        var mutableList: MutableList<DataModel.GalleryEntity> = mutableListOf()
        RetrofitClient.userImageList.getSearchImage(query, sort, page, size).documents?.toGalleryEntity()
            ?.let { mutableList.addAll(it) }
        RetrofitClient.userImageList.getSearchVideo(query, sort, page, size).documents?.toGalleryEntity()
            ?.let { mutableList.addAll(it) }
        mutableList = mutableList.sortList().toMutableList()
        if(page == 1) dataList.removeAll(dataList)
        dataList.addAll(mutableList)

        Log.d("Check Response", "도착된 데이터들: $dataList")
    }

    fun getApiData(): List<DataModel.GalleryEntity> = dataList
}

fun List<DataModel.GalleryEntity>.sortList() : List<DataModel.GalleryEntity> {
    Log.d("sorted?", "$this")
    return this.sortedWith(
        compareByDescending {
            it.dateTime
        }
    )
}