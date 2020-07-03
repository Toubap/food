package com.baptisterssl.food.view.meals

import com.baptisterssl.food.domain.meal.FetchMealsUseCase

interface MealUseCaseProvider {

    fun fetchMeals(): FetchMealsUseCase
}