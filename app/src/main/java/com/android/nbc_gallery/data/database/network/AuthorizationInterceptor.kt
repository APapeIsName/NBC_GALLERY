package com.android.nbc_gallery.data.database.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "KakaoAK %s".format("28eb30a5fe9af23d769acf0ad7552a45")
            )
            .build()
        return chain.proceed(newRequest)
    }
}