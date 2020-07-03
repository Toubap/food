package com.baptisterssl.food.domain.meal

import com.baptisterssl.food.data.entity.Meal

interface FetchMealsUseCase {

    suspend fun execute(): List<Meal>
}