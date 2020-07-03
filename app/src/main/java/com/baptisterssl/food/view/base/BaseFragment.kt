package com.baptisterssl.food.view.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.baptisterssl.food.App
import com.baptisterssl.food.data.repository.MealRepository

@SuppressLint("Registered")
open class BaseFragment : Fragment() {

    lateinit var mealRepository: MealRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(requireContext().applicationContext as App) {
            this@BaseFragment.mealRepository = mealRepository
        }
    }
}