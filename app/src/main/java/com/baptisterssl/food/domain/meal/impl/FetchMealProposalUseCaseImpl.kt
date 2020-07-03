package com.baptisterssl.food.domain.meal.impl

import com.baptisterssl.food.data.entity.Meal
import com.baptisterssl.food.data.entity.Type
import com.baptisterssl.food.data.repository.MealRepository
import com.baptisterssl.food.domain.meal.FetchMealProposalUseCase
import com.baptisterssl.food.domain.meal.FetchMealProposalUseCase.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.math.absoluteValue

class FetchMealProposalUseCaseImpl(
    private val mealRepository: MealRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : FetchMealProposalUseCase {

    override suspend fun execute(calorieLimit: Float): Result =
        withContext(coroutineDispatcher) {
            val meals = mealRepository.fetchMeals()
            var attempts = 0
            var calorieResult = 0F
            var starter: Meal
            var dish: Meal
            var desert: Meal

            while (calorieResult == 0F || calorieResult > calorieLimit && attempts <= 15) {
                meals.shuffled().run {
                    starter = first { it.type == Type.STARTER }
                    dish = first { it.type == Type.DISH }
                    desert = first { it.type == Type.DESERT }
                }
                calorieResult = (starter.cal + dish.cal + desert.cal).toFloat()

                if (calorieResult <= calorieLimit)
                    return@withContext Result.Success(starter, dish, desert)
                attempts++
            }

            return@withContext Result.None
        }
}