package com.homelandpay.codigodietplan.data.modal

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HealthConcernEntity(
    @SerializedName("data")
    val healthConcern: List<HealthConcern>
){
    data class HealthConcern(
        val id: Int,
        val name: String
    )
}

