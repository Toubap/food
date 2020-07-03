package com.baptisterssl.food.view.mealprep

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MealPrepViewModelFactory(private val useCaseProvider: MealPrepUseCaseProvider) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MealPrepViewModel(useCaseProvider) as T
    }
}