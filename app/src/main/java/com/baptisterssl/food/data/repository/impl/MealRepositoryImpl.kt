package com.baptisterssl.food.data.repository.impl

import com.baptisterssl.food.data.entity.Meal
import com.baptisterssl.food.data.remote.FoodAPIService
import com.baptisterssl.food.data.repository.MealRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MealRepositoryImpl(
    private val foodAPIService: FoodAPIService,
    private val coroutineDispatcher: CoroutineDispatcher
) : MealRepository {

    override suspend fun fetchMeals(): List<Meal> =
        withContext(coroutineDispatcher) {
            return@withContext foodAPIService.fetchMeals("bar")
        }
}