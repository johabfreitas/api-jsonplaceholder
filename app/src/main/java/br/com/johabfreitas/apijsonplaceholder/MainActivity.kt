package br.com.johabfreitas.apijsonplaceholder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.johabfreitas.apijsonplaceholder.databinding.ActivityMainBinding
import br.com.johabfreitas.apijsonplaceholder.model.Comments
import br.com.johabfreitas.apijsonplaceholder.model.Posts
import br.com.johabfreitas.apijsonplaceholder.service.PostsService
import br.com.johabfreitas.apijsonplaceholder.service.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        RetrofitHelper.retrofit
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnPosts.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main){
                    recuperarPost() //Recupera postagem pelo id
                    binding.edtId.getText().clear()
                }
            }
        }

        binding.btnListarposts.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main){
                    recuperarListaPosts() //Recupera uma lista de postagens
                    binding.edtId.getText().clear()
                }
            }
        }

        binding.btnComments.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main){
                    recuperarComentarios()
                }
            }
        }
    }

    private suspend fun recuperarPost() {

        var retorno: Response<Posts>? = null
        val idPostagem = binding.edtId.text.toString()

        try {
            val idPostagemInteira = Integer.parseInt(idPostagem)
            val postsService = retrofit.create(PostsService::class.java)
            retorno = postsService.recuperarPost(idPostagemInteira)

        }catch (e: Exception) {
            e.printStackTrace()
        } catch (e: NumberFormatException){
             e.printStackTrace()
        }

        if(retorno != null) {

            if(retorno.isSuccessful){

                val postagem = retorno.body()

                binding.edtVisualizar.setText(
                    " Title: \n" + postagem?.title + "\n \n" +
                            " Description: \n" + postagem?.description
                )
            }
        }
    }

    private suspend fun recuperarListaPosts() {

        var retorno: Response<List<Posts>>? = null

        try {
            val postsService = retrofit.create(PostsService::class.java)
            retorno = postsService.recuperarListaPosts()

        }catch (e: Exception) {
            e.printStackTrace()
        }

        if(retorno != null) {

            if(retorno.isSuccessful){

                val listaPostagens = retorno.body()

                var resultado = ""
                listaPostagens?.forEach{postagem ->
                    val idPosts = postagem.id
                    val titulo = postagem.title
                    val listaResultado = "$idPosts- $titulo \n"
                    resultado += listaResultado
                }

                binding.edtVisualizar.setText(resultado)

            }
        }

    }

    private suspend fun recuperarComentarios() {

        var retorno: Response<List<Comments>>? = null
        val idPostagem = binding.edtId.text.toString()

        try {
            val idPostagemInteira = Integer.parseInt(idPostagem)
            val postsService = retrofit.create(PostsService::class.java)
            retorno = postsService.recuperarComentarios(idPostagemInteira)

        }catch (e: Exception) {
            e.printStackTrace()
        } catch (e: NumberFormatException){
            e.printStackTrace()
        }

        if(retorno != null) {

            if(retorno.isSuccessful){

                val listaPostagens = retorno.body()

                var resultado = ""
                listaPostagens?.forEach{postagem ->
                    val email = postagem.email
                    val comentario = postagem.description
                    val listaResultado = "Email:\n $email \n Comentário:\n $comentario \n \n"
                    resultado += listaResultado
                }

                binding.edtVisualizar.setText(resultado)

            }

            /*if(retorno.isSuccessful){

                val postagem = retorno.body()

                binding.edtVisualizar.setText(
                    " Email: \n" + postagem?.email + "\n \n" +
                            " Description: \n" + postagem?.description
                )
            }*/
        }
    }
}