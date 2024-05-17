package br.com.johabfreitas.apijsonplaceholder.service

import br.com.johabfreitas.apijsonplaceholder.model.Comments
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
    suspend fun recuperarListaPosts() : Response<List<Posts>>

    //Recuperar comentários
    @GET("posts/{id}/comments")
    suspend fun recuperarComentarios(@Path("id") id: Int) : Response<List<Comments>>
}