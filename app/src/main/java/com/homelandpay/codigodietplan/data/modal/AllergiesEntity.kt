package com.homelandpay.codigodietplan.data.modal

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class AllergiesEntity(
    @SerializedName("data")
    val data: List<Data>
)

data class Data(
    val id: Int,
    val name: String
)
