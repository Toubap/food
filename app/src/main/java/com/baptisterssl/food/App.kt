package com.baptisterssl.food

import android.app.Application
import com.baptisterssl.food.data.remote.FoodAPIService
import com.baptisterssl.food.data.repository.MealRepository
import com.baptisterssl.food.data.repository.impl.MealRepositoryImpl
import kotlinx.coroutines.Dispatchers

open class App : Application() {

    lateinit var mealRepository: MealRepository

    open val foodAPIService: FoodAPIService
        get() = FoodAPIService.createService(getString(R.string.api_service_endpoint))

    override fun onCreate() {
        super.onCreate()

        mealRepository = MealRepositoryImpl(foodAPIService, Dispatchers.IO)
    }
}