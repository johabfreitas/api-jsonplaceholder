package br.com.johabfreitas.apijsonplaceholder.model

import com.google.gson.annotations.SerializedName

data class Posts(

    @SerializedName("body")
    val description: String,
    val id: Int,
    val title: String,
    val userId: Int
)