package com.baptisterssl.food.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer iwn-31@!3pf(w]pmarewj236^in")
            .build()
        return chain.proceed(request)
    }
}