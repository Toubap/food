package com.baptisterssl.food.data.remote

import com.baptisterssl.food.BuildConfig
import com.baptisterssl.food.data.entity.Meal
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface FoodAPIService {

    companion object {
        fun createService(url: String): FoodAPIService {
            @Suppress("ConstantConditionIf")
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .readTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
                        })
                        .addInterceptor(AuthInterceptor())
                        .build()
                )
                .baseUrl(url)
                .build()

            return retrofit.create()
        }
    }

    @GET("food/list")
    suspend fun fetchMeals(@Query("foo") foo: String): List<Meal>
}