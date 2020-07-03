package com.baptisterssl.food.domain.meal

import com.baptisterssl.food.data.entity.Meal

interface FetchMealProposalUseCase {

    sealed class Result {
        data class Success(val starter: Meal, val dish: Meal, val desert: Meal) : Result()
        object None : Result()
    }

    suspend fun execute(calorieLimit: Float): Result
}