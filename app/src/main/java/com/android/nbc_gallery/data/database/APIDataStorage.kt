package com.android.nbc_gallery.data.database

import android.util.Log
import com.android.nbc_gallery.data.database.dto.Document
import com.android.nbc_gallery.data.database.network.RetrofitClient

object APIDataStorage {
    private var dataList: List<Document?> = listOf()

    suspend fun getDataFromApi(
        query: String,
        sort: String = "accuracy",
        page: Int = 1,
        size: Int = 80,
    ) {
        dataList = RetrofitClient.userImageList.getSearchImage(query, sort, page, size).documents ?: listOf()
        Log.d("Check Response", "도착된 데이터들: $dataList")
    }

    fun getApiData(): List<Document?> {
        return dataList
    }
}