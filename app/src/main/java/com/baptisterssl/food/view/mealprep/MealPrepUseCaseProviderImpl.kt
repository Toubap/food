package com.baptisterssl.food.view.mealprep

import com.baptisterssl.food.data.repository.MealRepository
import com.baptisterssl.food.domain.meal.FetchMealProposalUseCase
import com.baptisterssl.food.domain.meal.GetMealPrepGradeUseCase
import com.baptisterssl.food.domain.meal.impl.FetchMealProposalUseCaseImpl
import com.baptisterssl.food.domain.meal.impl.GetMealPrepGradeUseCaseImpl
import kotlinx.coroutines.Dispatchers

class MealPrepUseCaseProviderImpl(private val mealRepository: MealRepository) :
    MealPrepUseCaseProvider {

    override fun fetchMealProposal(): FetchMealProposalUseCase =
        FetchMealProposalUseCaseImpl(mealRepository, Dispatchers.IO)

    override fun getMealPrepGrade(): GetMealPrepGradeUseCase =
        GetMealPrepGradeUseCaseImpl(Dispatchers.IO)
}