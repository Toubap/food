package com.baptisterssl.food.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Meal(
    val fibers: Double,
    val cal: Int,
    @SerializedName("display_name")
    val displayName: String,
    val imgUrl: String,
    val carbs: Double,
    val lipids: Double,
    val type: Type,
    val proteins: Double
) : Parcelable

enum class Type {
    @SerializedName("starter")
    STARTER,

    @SerializedName("dish")
    DISH,

    @SerializedName("desert")
    DESERT,
}