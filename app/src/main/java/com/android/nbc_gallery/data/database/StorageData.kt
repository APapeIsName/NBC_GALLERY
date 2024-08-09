package com.android.nbc_gallery.data.database

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.provider.ContactsContract.Data
import android.util.Log
import com.android.nbc_gallery.data.entity.DataModel
import com.android.nbc_gallery.presentation.mapper.toGalleryEntity
import com.android.nbc_gallery.presentation.uimodel.UiModel
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object StorageData {
    private val storageList: MutableList<DataModel.GalleryEntity> = mutableListOf()
    var storagePrefs : SharedPreferences? = null

    fun saveDataInLocal(context: Context, list: List<UiModel>) {
//        TODO("선택 구현에서 추가 예정: sharedPreference로 보관함 저장")
        if(storagePrefs == null) {
            initPrefs(context)
        }
        val entities = list.filterIsInstance<UiModel.GalleryModel>().toGalleryEntity()
        for(i in entities) {
            var isNotIn = true
            for(j in storageList) {
                if(i.imgUrl == j.imgUrl) {
                    isNotIn = false
                }
            }
            if(isNotIn) {
                Log.d("스토리지 들어오는 데이터", "$i")
                storageList.add(i)
            }
        }

        val editor = storagePrefs?.edit()
        val str = GsonBuilder().create().toJson(
            storageList,
            object : TypeToken<List<DataModel.GalleryEntity>>() {}.type
        )
        Log.d("스토리지 데이터 리스트", storageList.toString())
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
        for(i in list) {
            var isNotIn = true
            for(j in storageList) {
                if(i.imgUrl == j.imgUrl) {
                    isNotIn = false
                }
            }
            if(isNotIn) {
                Log.d("스토리지 들어오는 데이터", "$i")
                storageList.add(i)
            }
        }
        return getData()
    }

    fun deleteDataInLocal(data: DataModel.GalleryEntity) {
        for(i in storageList.indices) {
            if(data.imgUrl == storageList[i].imgUrl) {
                Log.d("좋아요 삭제", " 좋아요 삭 제 $storageList ")
                storageList.removeAt(i)
                Log.d("좋아요 삭제", " 좋아요 삭 제 이후 $storageList ")
            }
        }
    }

    fun getData(): List<DataModel> {
        return storageList
    }

    fun initPrefs(context: Context) {
        storagePrefs = context.getSharedPreferences("storage_set", MODE_PRIVATE)
    }

}

