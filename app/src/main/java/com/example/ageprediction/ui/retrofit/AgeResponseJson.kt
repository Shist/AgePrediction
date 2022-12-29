package com.example.ageprediction.ui.retrofit

import com.google.gson.annotations.SerializedName

data class AgeResponseJson(
    @SerializedName("age") val age: String?,
    @SerializedName("count") val count: String?,
    @SerializedName("name") val name: String?
)
