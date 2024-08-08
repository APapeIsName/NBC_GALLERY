package com.android.nbc_gallery.data.database

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import com.android.nbc_gallery.data.entity.DataModel
import com.android.nbc_gallery.presentation.mapper.toGalleryEntity
import com.android.nbc_gallery.presentation.uimodel.UiModel
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object StorageData {
    private var storageList: List<DataModel> = listOf()
    var storagePrefs : SharedPreferences? = null

    fun saveDataInLocal(context: Context, list: List<UiModel>) {
//        TODO("선택 구현에서 추가 예정: sharedPreference로 보관함 저장")
        if(storagePrefs == null) {
            initPrefs(context)
        }
        storageList = list.filterIsInstance<UiModel.GalleryModel>().toGalleryEntity()
        val editor = storagePrefs?.edit()
        val str = GsonBuilder().create().toJson(
            storageList,
            object : TypeToken<List<DataModel.GalleryEntity>>() {}.type
        )
        Log.d("ToJsonFF", str)
        editor?.putString("storage", str)
        editor?.apply()
    }

    fun loadDataInLocal(context: Context): List<DataModel> {
//        TODO("선택 구현에서 추가 예정: sharedPreference로 보관함 불러오기")
        if(storagePrefs == null) {
            initPrefs(context)
        }
        val str = storagePrefs?.getString("storage", "[]")
        val list: List<DataModel.GalleryEntity>
        if(str != null && str != "[]") {
            list = GsonBuilder().create().fromJson<List<DataModel.GalleryEntity>>(
                str, object : TypeToken<List<DataModel.GalleryEntity>>(){}.type
            )
            Log.d("FromJsonFF", "$str")
        } else {
             list = listOf()
        }

        storageList = list
        return getData()
    }

    fun getData(): List<DataModel> {
        return storageList
    }

    fun initPrefs(context: Context) {
        storagePrefs = context.getSharedPreferences("storage_set", MODE_PRIVATE)
    }

}

