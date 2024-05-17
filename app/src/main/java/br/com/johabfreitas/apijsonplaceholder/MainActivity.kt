package br.com.johabfreitas.apijsonplaceholder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.johabfreitas.apijsonplaceholder.databinding.ActivityMainBinding
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

                    //recuperarPosts() //Recupera uma lista de postagens
                    recuperarPost() //Recupera postagem pelo id

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
            throw NumberFormatException ("")

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

    private suspend fun recuperarPosts() {

        var retorno: Response<List<Posts>>? = null

        try {
            val postsService = retrofit.create(PostsService::class.java)
            retorno = postsService.recuperarPosts()

        }catch (e: Exception) {
            e.printStackTrace()
        }

        if(retorno != null) {

            if(retorno.isSuccessful){

                val listaPostagens = retorno.body()

                listaPostagens?.forEach{postagem ->
                    val id = postagem.id
                    val titulo = postagem.title

                    binding.edtVisualizar.setText(id.toString() + " - " + titulo)

                    //Log.i("info_jsonplace", "$id - $titulo")
                }
            }
        }

    }

    class CustomException(message: String) : Exception(message)
}