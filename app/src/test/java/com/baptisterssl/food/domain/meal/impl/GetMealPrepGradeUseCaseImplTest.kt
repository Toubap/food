package com.baptisterssl.food.domain.meal.impl

import com.baptisterssl.food.data.entity.Meal
import com.baptisterssl.food.data.entity.Type
import com.baptisterssl.food.domain.meal.GetMealPrepGradeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetMealPrepGradeUseCaseImplTest {

    private lateinit var useCase: GetMealPrepGradeUseCase

    private val meals = listOf(
        Meal(0.0, 50, "Salde de riz", "", 0.0, 0.0, Type.STARTER, 0.0),
        Meal(0.0, 230, "Burger et frites de patate douce", "", 0.0, 0.0, Type.DISH, 0.0),
        Meal(0.0, 200, "Gateau au chocolat", "", 0.0, 0.0, Type.DESERT, 0.0)
    )

    @Before
    fun setUp() {
        useCase = GetMealPrepGradeUseCaseImpl(Dispatchers.Unconfined)
    }

    @Test
    fun getGrade_Excellent() = runBlocking {
        val grade = useCase.execute(meals, 500F)
        assertTrue(grade == 'A')
    }

    @Test
    fun getGrade_Good() = runBlocking {
        val grade = useCase.execute(meals, 600F)
        assertTrue(grade == 'B')
    }

    @Test
    fun getGrade_Bad() = runBlocking {
        val grade = useCase.execute(meals, 650F)
        assertTrue(grade == 'C')
    }

    @Test
    fun getGrade_Terrible() = runBlocking {
        val grade = useCase.execute(meals, 800F)
        assertTrue(grade == 'D')
    }
}