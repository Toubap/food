package com.baptisterssl.food.data.repository

import com.baptisterssl.food.data.entity.Meal

interface MealRepository {

    suspend fun fetchMeals(): List<Meal>
}