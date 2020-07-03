package com.baptisterssl.food.view.mealprep

import androidx.lifecycle.*
import com.baptisterssl.food.data.entity.Meal
import com.baptisterssl.food.domain.meal.FetchMealProposalUseCase.Result
import kotlinx.coroutines.launch

class MealPrepViewModel(private val useCaseProvider: MealPrepUseCaseProvider) : ViewModel() {

    sealed class ViewState {
        data class MealProposal(
            val starter: Meal,
            val dish: Meal,
            val desert: Meal,
            val grade: Char
        ) : ViewState()

        object Progress : ViewState()
        object Error : ViewState()
        object NoProposal : ViewState()
    }

    data class Form(var calories: String = "")

    private val _viewStateLiveDate = MutableLiveData<ViewState>()
    val viewStateLiveData: LiveData<ViewState>
        get() = _viewStateLiveDate
    private var form = MutableLiveData<Form>().apply { value = Form() }

    val isFormButtonEnabledLiveData: LiveData<Boolean> = form.switchMap {
        liveData(viewModelScope.coroutineContext) {
            emit(it.calories.isNotBlank())
        }
    }

    fun fetchMealProposal(calorieLimit: Float) {
        viewModelScope.launch {
            try {
                updateViewState(ViewState.Progress)
                when (val result = useCaseProvider.fetchMealProposal().execute(calorieLimit)) {
                    is Result.Success -> {
                        val meals = listOf(result.starter, result.dish, result.desert)
                        val grade = useCaseProvider.getMealPrepGrade().execute(meals, calorieLimit)
                        updateViewState(
                            ViewState.MealProposal(
                                result.starter, result.dish, result.desert, grade
                            )
                        )
                    }
                    Result.None -> updateViewState(ViewState.NoProposal)
                }
            } catch (exception: Exception) {
                updateViewState(ViewState.Error)
            }
        }
    }

    fun onCaloriesInputChanged(value: String) {
        form.value = form.value?.apply { calories = value }
    }

    /** Private */

    private fun updateViewState(viewState: ViewState) {
        _viewStateLiveDate.value = viewState
    }
}