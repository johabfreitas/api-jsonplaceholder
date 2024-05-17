package br.com.johabfreitas.apijsonplaceholder.service

import br.com.johabfreitas.apijsonplaceholder.model.Posts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostsService {

    //Recupera postagem pelo id
    @GET("posts/{id}")
    suspend fun recuperarPost(@Path("id") id: Int) : Response<Posts>

    //Recupera uma lista de postagens
    @GET("posts")
    suspend fun recuperarPosts() : Response<List<Posts>>
}