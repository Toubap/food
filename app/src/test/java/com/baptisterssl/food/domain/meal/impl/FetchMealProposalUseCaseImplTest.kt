package com.baptisterssl.food.domain.meal.impl

import com.baptisterssl.food.data.entity.Meal
import com.baptisterssl.food.data.entity.Type
import com.baptisterssl.food.data.repository.MealRepository
import com.baptisterssl.food.domain.meal.FetchMealProposalUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FetchMealProposalUseCaseImplTest {

    private lateinit var useCase: FetchMealProposalUseCase

    private val mealRepository = mock<MealRepository>()

    private val meals = listOf(
        Meal(0.0, 200, "Salde de riz", "", 0.0, 0.0, Type.STARTER, 0.0),
        Meal(0.0, 250, "Salde de riz", "", 0.0, 0.0, Type.STARTER, 0.0),
        Meal(0.0, 180, "Burger et frites de patate douce", "", 0.0, 0.0, Type.DISH, 0.0),
        Meal(0.0, 230, "Burger et frites de patate douce", "", 0.0, 0.0, Type.DISH, 0.0),
        Meal(0.0, 290, "Gateau au chocolat", "", 0.0, 0.0, Type.DESERT, 0.0),
        Meal(0.0, 400, "Gateau au chocolat", "", 0.0, 0.0, Type.DESERT, 0.0)
    )

    @Before
    fun setUp() {
        useCase = FetchMealProposalUseCaseImpl(mealRepository, Dispatchers.Unconfined)
    }

    @Test
    fun noMealProposalFound() = runBlocking {
        whenever(mealRepository.fetchMeals()).thenReturn(meals)

        val result = useCase.execute(500F)

        assertTrue(result is FetchMealProposalUseCase.Result.None)
    }

    @Test
    fun foundProposal() = runBlocking {
        whenever(mealRepository.fetchMeals()).thenReturn(meals)

        val result = useCase.execute(2500F)

        assertTrue(result is FetchMealProposalUseCase.Result.Success)
        result as FetchMealProposalUseCase.Result.Success
        assertTrue(result.starter in meals.filter { it.type == Type.STARTER })
        assertTrue(result.dish in meals.filter { it.type == Type.DISH })
        assertTrue(result.desert in meals.filter { it.type == Type.DESERT })
    }
}