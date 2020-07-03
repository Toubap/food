package com.baptisterssl.food.domain.meal.impl

import com.baptisterssl.food.data.entity.Meal
import com.baptisterssl.food.domain.meal.GetMealPrepGradeUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.math.absoluteValue

class GetMealPrepGradeUseCaseImpl(private val coroutineDispatcher: CoroutineDispatcher) :
    GetMealPrepGradeUseCase {

    override suspend fun execute(meals: List<Meal>, calorieLimit: Float): Char =
        withContext(coroutineDispatcher) {
            val calories = meals.map { it.cal }.sum()
            val result = (((calorieLimit - calories) / calorieLimit) * 100).absoluteValue
            return@withContext when {
                result <= 10F -> 'A'
                result in 11F..20F -> 'B'
                result in 21F..30F -> 'C'
                else -> 'D'
            }
        }
}