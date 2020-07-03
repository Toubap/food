package com.baptisterssl.food.view.meals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MealViewModelFactory(private val useCaseProvider: MealUseCaseProvider)
    : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MealViewModel(useCaseProvider) as T
    }
}