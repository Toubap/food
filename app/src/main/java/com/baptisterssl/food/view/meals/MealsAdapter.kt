package com.baptisterssl.food.view.meals

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.baptisterssl.food.R
import com.baptisterssl.food.data.entity.Meal
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_list_meal.view.*
import java.util.*

class MealsAdapter(private val listener: ViewHolder.Listener) :
    RecyclerView.Adapter<MealsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        interface Listener {
            fun onClickMeal(meal: Meal)
        }

        fun bind(meal: Meal, listener: Listener) {
            itemView.run {
                Glide.with(this).load(meal.imgUrl).into(imageViewMeal)
                textViewMealName.text = meal.displayName
                textViewType.text = meal.type.name.toLowerCase(Locale.getDefault())
                @SuppressLint("SetTextI18n")
                textViewCalories.text = "${meal.cal} kcal"
                setOnClickListener { listener.onClickMeal(meal) }
            }
        }
    }

    private val data = arrayListOf<Meal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_meal, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = data[position]
        holder.bind(meal, listener)
    }

    fun update(dataSet: List<Meal>) {
        this.data.clear()
        this.data.addAll(dataSet)
        notifyDataSetChanged()
    }
}