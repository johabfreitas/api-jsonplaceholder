package br.com.johabfreitas.apijsonplaceholder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
                    limparEntrada()
                }
            }
        }

        binding.btnListarposts.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main){
                    recuperarListaPosts() //Recupera uma lista de postagens
                    limparEntrada()
                }
            }
        }

        binding.btnComments.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main){
                    recuperarComentariosPath()
                    limparEntrada()
                }
            }
        }

        binding.btnCommentsQuery.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main){
                    recuperarComentariosQuery()
                    limparEntrada()
                }
            }
        }
    }

    // Limpar entrada
    private fun limparEntrada() {
        binding.edtId.getText().clear()
    }

    // Recuperar uma postagem pelo iD.
    private suspend fun recuperarPost() {

        var retorno: Response<Posts>? = null
        val idPostagem = binding.edtId.text.toString()

        try {
            val idPostagemInteira = Integer.parseInt(idPostagem)
            val postsService = retrofit.create(PostsService::class.java)
            retorno = postsService.recuperarPost(idPostagemInteira)

        }catch (e: Exception) {
            e.printStackTrace()
        } catch (f: NumberFormatException){
             exibirMensagem("Entrada inválida entre com um iD!")
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

    //Mensagem personalizada para o catch
    private fun exibirMensagem(mensagem: String){
        Toast.makeText(applicationContext, mensagem, Toast.LENGTH_SHORT).show()
    }

    //Recuperar uma lista de postagem completa.
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

    //Recuperar postagem com Path
    private suspend fun recuperarComentariosPath() {

        var retorno: Response<List<Comments>>? = null
        val idPostagem = binding.edtId.text.toString()

        try {
            val idPostagemInteira = Integer.parseInt(idPostagem)
            val postsService = retrofit.create(PostsService::class.java)
            retorno = postsService.recuperarComentariosPath(idPostagemInteira)

        }catch (e: Exception) {
            e.printStackTrace()
        } catch (error: NumberFormatException){
            throw NumberFormatException("Entrada inválida, digite um iD! ${error.message}")
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

    // Recuperar postagem com Query
    private suspend fun recuperarComentariosQuery() {

        var retorno: Response<List<Comments>>? = null
        val idPostagem = binding.edtId.text.toString()

        try {
            val idPostagemInteira = Integer.parseInt(idPostagem)
            val postsService = retrofit.create(PostsService::class.java)
            retorno = postsService.recuperarComentariosQuery(idPostagemInteira)

        }catch (e: Exception) {
            e.printStackTrace()
        } catch (error: NumberFormatException){
            throw NumberFormatException("Entrada inválida, digite um iD! ${error.message}")
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