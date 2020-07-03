package com.baptisterssl.food.domain.meal.impl

import com.baptisterssl.food.data.entity.Meal
import com.baptisterssl.food.data.repository.MealRepository
import com.baptisterssl.food.domain.meal.FetchMealsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FetchMealsUseCaseImpl(
    private val mealRepository: MealRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : FetchMealsUseCase {

    override suspend fun execute(): List<Meal> =
        withContext(coroutineDispatcher) {
            mealRepository.fetchMeals()
        }
}