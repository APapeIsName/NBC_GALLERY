package com.android.nbc_gallery.data.database

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.android.nbc_gallery.data.entity.DataModel

object StorageData {
    private val storageList: MutableList<DataModel> = mutableListOf()
    var storagePrefs : SharedPreferences? = null

    fun saveDataInLocal(context: Context) {
//        TODO("선택 구현에서 추가 예정: sharedPreference로 보관함 저장")
        if(storagePrefs == null) {
            initPrefs(context)
        }
        val editor = storagePrefs?.edit()
    }

    fun loadDataInLocal(context: Context) {
//        TODO("선택 구현에서 추가 예정: sharedPreference로 보관함 불러오기")
        if(storagePrefs == null) {
            initPrefs(context)
        }

    }

    fun initPrefs(context: Context) {
        storagePrefs = context.getSharedPreferences("storage_list", MODE_PRIVATE)
    }

}

