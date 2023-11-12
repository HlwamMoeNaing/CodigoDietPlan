package com.homelandpay.codigodietplan.data.modal

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DietEntity(
    @SerializedName("data")
    val diets: List<Diets>
){
    data class Diets(
        val id: Int,
        val name: String,
        val tool_tip: String
    )
}

