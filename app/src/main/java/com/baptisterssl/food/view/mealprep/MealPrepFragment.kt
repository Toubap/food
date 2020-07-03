package com.baptisterssl.food.view.mealprep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.baptisterssl.food.R
import com.baptisterssl.food.data.entity.Meal
import com.baptisterssl.food.view.base.BaseFragment
import com.baptisterssl.food.view.meal.MealActivity
import com.baptisterssl.food.view.mealprep.MealPrepViewModel.ViewState
import com.baptisterssl.food.view.meals.MealsAdapter
import com.baptisterssl.food.view.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_meal_prep.*
import kotlinx.android.synthetic.main.view_meal_prep_form.*
import kotlinx.android.synthetic.main.view_meal_prep_result.*
import kotlinx.android.synthetic.main.view_meals_success.*
import kotlinx.android.synthetic.main.view_meals_success.view.*

class MealPrepFragment : BaseFragment(), MealsAdapter.ViewHolder.Listener {

    private val viewModel by viewModels<MealPrepViewModel> {
        MealPrepViewModelFactory(MealPrepUseCaseProviderImpl(mealRepository))
    }
    private lateinit var mealsAdapter: MealsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_meal_prep, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
        observeViewState()
    }

    /** MealsAdapter.ViewHolder.Listener */

    override fun onClickMeal(meal: Meal) {
        startActivity(MealActivity.newIntent(requireContext(), meal))
    }

    /** Private */

    private fun initView() {
        mealsAdapter = MealsAdapter(this)
        recyclerViewMeals.run {
            setHasFixedSize(true)
            adapter = mealsAdapter
        }

        textInputCalories?.editText?.run {
            doAfterTextChanged {
                viewModel.onCaloriesInputChanged(it?.toString() ?: "")
            }
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (buttonGetMealProposal.isEnabled)
                        buttonGetMealProposal.performClick()
                    true
                } else false
            }
        }
        buttonGetMealProposal.setOnClickListener {
            textInputCalories?.hideKeyboard()
            viewModel.fetchMealProposal(textInputCalories.editText!!.text.toString().toFloat())
        }

        viewResult.recyclerViewMeals.isNestedScrollingEnabled = false
    }

    private fun observeViewState() {
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewState.MealProposal -> {
                    mealsAdapter.update(listOf(viewState.starter, viewState.dish, viewState.desert))
                    textViewGrade.text = viewState.grade.toString()
                    val textColor = when (viewState.grade) {
                        'A' -> R.color.grade_excellent
                        'B' -> R.color.grade_good
                        'C' -> R.color.grade_bad
                        else -> R.color.grade_terrible
                    }
                    textViewGrade.setTextColor(ContextCompat.getColor(requireContext(), textColor))
                    updateViewState(hasData = true)
                }
                ViewState.Progress -> updateViewState(isLoading = true)
                ViewState.Error -> updateViewState(hasError = true)
                ViewState.NoProposal -> updateViewState(isEmpty = true)
            }
        }

        viewModel.isFormButtonEnabledLiveData.observe(viewLifecycleOwner) { isEnabled ->
            buttonGetMealProposal.isEnabled = isEnabled
        }
    }

    private fun updateViewState(
        hasData: Boolean = false,
        hasError: Boolean = false,
        isLoading: Boolean = false,
        isEmpty: Boolean = false
    ) {
        viewResult.isVisible = hasData && !hasError && !isLoading
        viewError.isVisible = !hasData && hasError && !isLoading
        viewRetry.isVisible = !hasData && !hasError && !isLoading && isEmpty
        progressMealPrep.isVisible = isLoading
    }
}