package com.baptisterssl.food.view.meals

import com.baptisterssl.food.data.repository.MealRepository
import com.baptisterssl.food.domain.meal.FetchMealsUseCase
import com.baptisterssl.food.domain.meal.impl.FetchMealsUseCaseImpl
import kotlinx.coroutines.Dispatchers

class MealUseCaseProviderImpl(private val mealRepository: MealRepository) : MealUseCaseProvider {

    override fun fetchMeals(): FetchMealsUseCase =
        FetchMealsUseCaseImpl(mealRepository, Dispatchers.IO)
}