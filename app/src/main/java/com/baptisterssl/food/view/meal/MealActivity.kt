package com.baptisterssl.food.view.meal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.baptisterssl.food.R
import com.baptisterssl.food.data.entity.Meal
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_meal.*

class MealActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context, meal: Meal): Intent =
            Intent(context, MealActivity::class.java).apply {
                putExtra(KEY_MEAL, meal)
            }

        private const val KEY_MEAL = "KEY_MEAL"
    }

    private val meal by lazy { intent.getParcelableExtra(KEY_MEAL) as Meal }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)
        setSupportActionBar(toolBarMeal)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /** Private */

    private fun initView() {
        title = meal.displayName
        Glide.with(this).load(meal.imgUrl).into(imageViewMeal)
        textViewCalories.text = "${meal.cal}kcl"
    }
}