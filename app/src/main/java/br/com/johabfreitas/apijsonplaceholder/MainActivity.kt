package br.com.johabfreitas.apijsonplaceholder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.johabfreitas.apijsonplaceholder.databinding.ActivityMainBinding
import br.com.johabfreitas.apijsonplaceholder.service.RetrofitHelper

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
    }
}