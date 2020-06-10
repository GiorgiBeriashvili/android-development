package dev.beriashvili.assignments.mvvm.models

import com.google.gson.annotations.SerializedName

data class Apartment(
    @SerializedName("descriptionEN") val description: String?,
    @SerializedName("titleEN") val title: String?,
    val cover: String?
)