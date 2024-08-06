package com.android.nbc_gallery.data.database.network

import com.android.nbc_gallery.data.database.dto.Document
import com.android.nbc_gallery.data.database.dto.GalleryDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("/v2/search/image")
    suspend fun getSearchImage(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ) : GalleryDTO
}