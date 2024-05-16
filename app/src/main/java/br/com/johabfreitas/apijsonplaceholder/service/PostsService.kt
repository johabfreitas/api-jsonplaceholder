package br.com.johabfreitas.apijsonplaceholder.service

import br.com.johabfreitas.apijsonplaceholder.model.Posts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostsService {

    @GET("posts/{id}")
    suspend fun recuperarPosts(@Path("id") id: Int) : Response<List<Posts>>
}