package br.com.johabfreitas.apijsonplaceholder.service

import br.com.johabfreitas.apijsonplaceholder.model.Comments
import br.com.johabfreitas.apijsonplaceholder.model.Posts
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsService {

    //Recupera postagem pelo id
    @GET("posts/{id}")
    suspend fun recuperarPost(@Path("id") id: Int) : Response<Posts>

    //Recupera uma lista de postagens completa
    @GET("posts")
    suspend fun recuperarListaPosts() : Response<List<Posts>>

    //Recuperar comentários com Path
    @GET("posts/{id}/comments")
    suspend fun recuperarComentariosPath(@Path("id") id: Int) : Response<List<Comments>>

    //Recuperar comentários com Query
    @GET("comments")
    suspend fun recuperarComentariosQuery(@Query("id") id: Int) : Response<List<Comments>>

    //Salvar uma postagem
    @POST("posts")
    suspend fun salvarPosts(@Body posts: Posts): Response<Posts>

    //Atualizar postagem PATCH
    @PATCH("posts/{id}")
    suspend fun atualizarPostsPatch(@Path("id") id:Int, @Body posts: Posts): Response<Posts>

    @PUT("posts/{id}")
    suspend fun atualizarPostsPut(@Path("id") id:Int, @Body posts: Posts): Response<Posts>

    @DELETE("posts/{id}")
    suspend fun removerPosts(@Path("id") id:Int): Response<Posts>
}