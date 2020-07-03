package com.baptisterssl.food.view.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.baptisterssl.food.R
import com.baptisterssl.food.data.entity.Meal
import com.baptisterssl.food.view.base.BaseFragment
import com.baptisterssl.food.view.meal.MealActivity
import kotlinx.android.synthetic.main.fragment_meals.*
import kotlinx.android.synthetic.main.view_meals_error.*
import kotlinx.android.synthetic.main.view_meals_success.*

class MealsFragment : BaseFragment(), MealsAdapter.ViewHolder.Listener {

    private val viewModel by viewModels<MealViewModel> {
        MealViewModelFactory(MealUseCaseProviderImpl(mealRepository))
    }
    private lateinit var mealsAdapter: MealsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_meals, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
        observeViewState()

        viewModel.loadMeals()
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

        buttonRetry.setOnClickListener { viewModel.loadMeals() }
    }

    private fun observeViewState() {
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is MealViewModel.ViewState.Meals -> {
                    mealsAdapter.update(viewState.meals)
                    updateViewState(hasData = true)
                }
                MealViewModel.ViewState.Progress -> updateViewState(isLoading = true)
                MealViewModel.ViewState.Error -> updateViewState(hasError = true)
            }
        }
    }

    private fun updateViewState(
        hasData: Boolean = false,
        hasError: Boolean = false,
        isLoading: Boolean = false
    ) {
        viewMeals.isVisible = hasData && !hasError && !isLoading
        viewMealsError.isVisible = !hasData && hasError && !isLoading
        progressMeals.isVisible = isLoading
    }
}