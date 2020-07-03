package com.baptisterssl.food.view.meals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baptisterssl.food.data.entity.Meal
import kotlinx.coroutines.launch

class MealViewModel(private val useCaseProvider: MealUseCaseProvider) : ViewModel() {

    sealed class ViewState {
        data class Meals(val meals: List<Meal>) : ViewState()
        object Progress : ViewState()
        object Error : ViewState()
    }

    private val _viewStateLiveDate = MutableLiveData<ViewState>()
    val viewStateLiveData: LiveData<ViewState>
        get() = _viewStateLiveDate

    fun loadMeals() {
        fetchMeals()
    }

    /** Private */

    private fun fetchMeals() {
        viewModelScope.launch {
            try {
                updateViewState(ViewState.Progress)
                val result = useCaseProvider.fetchMeals().execute()
                updateViewState(ViewState.Meals(result))
            } catch (exception: Exception) {
                updateViewState(ViewState.Error)
            }
        }
    }

    private fun updateViewState(viewState: ViewState) {
        _viewStateLiveDate.value = viewState
    }
}