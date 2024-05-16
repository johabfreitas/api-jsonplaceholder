package br.com.johabfreitas.apijsonplaceholder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

                    recuperarPosts()

                }
            }
        }
    }

    private suspend fun recuperarPosts() {

        var retorno: Response<List<Posts>>? = null
        val idPostagem = binding.edtId.text.toString()
        val idPostagemInteira = Integer.parseInt(idPostagem)

        try {
            val postsService = retrofit.create(PostsService::class.java)
            retorno = postsService.recuperarPosts(idPostagemInteira)

        }catch (e: Exception) {
            e.printStackTrace()
        }

        if(retorno != null) {

            if(retorno.isSuccessful){

                val listaPostagens = retorno.body()

                listaPostagens?.forEach{postagem ->
                    /*val id = postagem.id
                    val titulo = postagem.title
                    val saida = "$id- $titulo"
                    Log.i("info_jsonplace", "$id - $titulo")*/
                    val saida = listaPostagens.get(postagem.id)
                    binding.edtVisualizar.setText(saida.title)
                    Log.i("info_jsonplace", "$saida")
                }


            }
        }

    }
}