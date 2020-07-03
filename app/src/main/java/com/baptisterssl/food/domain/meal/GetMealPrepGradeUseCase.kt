package com.baptisterssl.food.domain.meal

import com.baptisterssl.food.data.entity.Meal

interface GetMealPrepGradeUseCase {

    suspend fun execute(meals: List<Meal>, calorieLimit: Float): Char
}