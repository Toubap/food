package com.baptisterssl.food.view.meals

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.baptisterssl.food.data.entity.Meal
import com.baptisterssl.food.data.entity.Type
import com.baptisterssl.food.domain.meal.FetchMealsUseCase
import com.baptisterssl.food.utils.CoroutineTestRule
import com.baptisterssl.food.view.meals.MealViewModel.ViewState
import com.jraska.livedata.TestLifecycle
import com.jraska.livedata.TestObserver
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MealViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: MealViewModel

    private val useCaseProvider = mock<MealUseCaseProvider>()
    private val fetchMealsUseCase = mock<FetchMealsUseCase>()

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
        viewModel = MealViewModel(useCaseProvider)

        whenever(useCaseProvider.fetchMeals()).thenReturn(fetchMealsUseCase)
    }

    @Test
    fun fetchMeals_Success() {
        val testObserver = TestObserver.create<ViewState>()
        val testLifecycle = TestLifecycle.initialized()

        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(fetchMealsUseCase.execute()).thenReturn(meals)

            viewModel.viewStateLiveData.observe(testLifecycle, testObserver)
            testLifecycle.resume()

            viewModel.loadMeals()
        }

        testObserver.assertValueHistory(
            ViewState.Progress,
            ViewState.Meals(meals)
        )

        viewModel.viewStateLiveData.removeObservers(testLifecycle)
    }

    @Test
    fun fetchMeals_Error() {
        val testObserver = TestObserver.create<ViewState>()
        val testLifecycle = TestLifecycle.initialized()

        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(fetchMealsUseCase.execute()).thenThrow(RuntimeException())

            viewModel.viewStateLiveData.observe(testLifecycle, testObserver)
            testLifecycle.resume()

            viewModel.loadMeals()
        }

        testObserver.assertValueHistory(
            ViewState.Progress,
            ViewState.Error
        )

        viewModel.viewStateLiveData.removeObservers(testLifecycle)
    }
}