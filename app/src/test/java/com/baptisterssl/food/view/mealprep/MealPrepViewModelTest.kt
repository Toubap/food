package com.baptisterssl.food.view.mealprep

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.baptisterssl.food.data.entity.Meal
import com.baptisterssl.food.data.entity.Type
import com.baptisterssl.food.domain.meal.FetchMealProposalUseCase
import com.baptisterssl.food.domain.meal.GetMealPrepGradeUseCase
import com.baptisterssl.food.utils.CoroutineTestRule
import com.baptisterssl.food.view.mealprep.MealPrepViewModel.ViewState
import com.jraska.livedata.TestLifecycle
import com.jraska.livedata.TestObserver
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MealPrepViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: MealPrepViewModel

    private val useCaseProvider = mock<MealPrepUseCaseProvider>()
    private val fetchMealProposalUseCase = mock<FetchMealProposalUseCase>()
    private val getMealPrepGradeUseCase = mock<GetMealPrepGradeUseCase>()

    @Before
    fun setUp() {
        viewModel = MealPrepViewModel(useCaseProvider)
        whenever(useCaseProvider.fetchMealProposal()).thenReturn(fetchMealProposalUseCase)
        whenever(useCaseProvider.getMealPrepGrade()).thenReturn(getMealPrepGradeUseCase)
    }

    @Test
    fun onCalorieInputChanged() {
        val testObserver = TestObserver.create<Boolean>()
        val testLifecycle = TestLifecycle.initialized()

        coroutineTestRule.testDispatcher.runBlockingTest {
            viewModel.isFormButtonEnabledLiveData.observe(testLifecycle, testObserver)
            testLifecycle.resume()

            viewModel.onCaloriesInputChanged(" ")
            viewModel.onCaloriesInputChanged("")
            viewModel.onCaloriesInputChanged("5")
            viewModel.onCaloriesInputChanged("50")
            viewModel.onCaloriesInputChanged("500")
        }

        // +1 value in the history because of the LiveData initialization
        testObserver.assertValueHistory(false, false, false, true, true, true)

        viewModel.isFormButtonEnabledLiveData.removeObservers(testLifecycle)
    }

    @Test
    fun fetchProposal_Success() {
        val testObserver = TestObserver.create<ViewState>()
        val testLifecycle = TestLifecycle.initialized()
        val starter = Meal(0.0, 200, "Salde de riz", "", 0.0, 0.0, Type.STARTER, 0.0)
        val dish = Meal(0.0, 180, "Burger et frites de patate douce", "", 0.0, 0.0, Type.DISH, 0.0)
        val desert = Meal(0.0, 400, "Gateau au chocolat", "", 0.0, 0.0, Type.DESERT, 0.0)
        val grade = 'C'

        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(fetchMealProposalUseCase.execute(any())).thenReturn(
                FetchMealProposalUseCase.Result.Success(starter, dish, desert)
            )
            whenever(getMealPrepGradeUseCase.execute(listOf(starter, dish, desert), 500F))
                .thenReturn(grade)

            viewModel.viewStateLiveData.observe(testLifecycle, testObserver)
            testLifecycle.resume()

            viewModel.fetchMealProposal(500F)
        }

        testObserver.assertValueHistory(
            ViewState.Progress,
            ViewState.MealProposal(starter, dish, desert, grade)
        )

        viewModel.viewStateLiveData.removeObservers(testLifecycle)
    }

    @Test
    fun fetchProposal_None() {
        val testObserver = TestObserver.create<ViewState>()
        val testLifecycle = TestLifecycle.initialized()

        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(fetchMealProposalUseCase.execute(any())).thenReturn(
                FetchMealProposalUseCase.Result.None
            )

            viewModel.viewStateLiveData.observe(testLifecycle, testObserver)
            testLifecycle.resume()

            viewModel.fetchMealProposal(500F)
        }

        testObserver.assertValueHistory(
            ViewState.Progress,
            ViewState.NoProposal
        )

        viewModel.viewStateLiveData.removeObservers(testLifecycle)
    }

    @Test
    fun fetchProposal_Error() {
        val testObserver = TestObserver.create<ViewState>()
        val testLifecycle = TestLifecycle.initialized()

        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(fetchMealProposalUseCase.execute(any())).thenThrow(RuntimeException())

            viewModel.viewStateLiveData.observe(testLifecycle, testObserver)
            testLifecycle.resume()

            viewModel.fetchMealProposal(500F)
        }

        testObserver.assertValueHistory(
            ViewState.Progress,
            ViewState.Error
        )

        viewModel.viewStateLiveData.removeObservers(testLifecycle)
    }
}