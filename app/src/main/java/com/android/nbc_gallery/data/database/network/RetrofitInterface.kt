package com.android.nbc_gallery.data.database.network

import com.android.nbc_gallery.data.database.dto.img.GalleryDTO
import com.android.nbc_gallery.data.database.dto.video.VideoDTO
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
    @GET("v2/search/vclip")
    suspend fun getSearchVideo(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ) : VideoDTO
}