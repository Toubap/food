package com.baptisterssl.food.view.mealprep

import com.baptisterssl.food.domain.meal.FetchMealProposalUseCase
import com.baptisterssl.food.domain.meal.GetMealPrepGradeUseCase

interface MealPrepUseCaseProvider {

    fun fetchMealProposal(): FetchMealProposalUseCase
    fun getMealPrepGrade(): GetMealPrepGradeUseCase
}