package br.com.johabfreitas.apijsonplaceholder.model

import com.google.gson.annotations.SerializedName

data class Comments(

    @SerializedName("body")
    val description: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)