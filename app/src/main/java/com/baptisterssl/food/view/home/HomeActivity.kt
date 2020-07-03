package com.baptisterssl.food.view.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.baptisterssl.food.R
import com.baptisterssl.food.view.mealprep.MealPrepFragment
import com.baptisterssl.food.view.meals.MealsFragment
import com.baptisterssl.food.view.utils.replaceFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val KEY_MENU_SELECTION = "KEY_MENU_SELECTION"
    }

    private val navigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener
        get() = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val fragment: Fragment? = when (item.itemId) {
                R.id.actionMeals -> MealsFragment()
                R.id.actionMealPrep -> MealPrepFragment()
                else -> null
            }
            fragment?.let {
                currentPage = item.itemId
                supportFragmentManager.replaceFragment(it, R.id.containerFragment)
                true
            } ?: false
        }

    private var currentPage = R.id.actionMeals

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        initMenu()

        bottomNavigationView.selectedItemId =
            savedInstanceState?.getInt(KEY_MENU_SELECTION) ?: currentPage
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_MENU_SELECTION, bottomNavigationView.selectedItemId)
    }

    /** Private */

    private fun initMenu() {
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
    }
}