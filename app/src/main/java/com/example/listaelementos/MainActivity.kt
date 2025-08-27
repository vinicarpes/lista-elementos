package com.example.listaelementos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.listaelementos.database.database
import com.example.listaelementos.databinding.ActivityMainBinding
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.ProdutoRepository
import com.example.listaelementos.repositories.toProduto
import com.example.listaelementos.ui.activities.CadastroActivity
import com.example.listaelementos.ui.adapters.ProdutoAdapter
import com.example.listaelementos.ui.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    var produtos: List<Produto> = emptyList()
    private lateinit var viewModel : MainViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = MainViewModel(applicationContext)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val produtosAdapter = ProdutoAdapter(
            context = this
        )
        viewModel.produtos.observe(this) { produtos ->
            produtosAdapter.setList(produtos)
            binding.txtTotal.text = "TOTAL: ${viewModel.updateTotal(produtos)}"
        }
        viewModel.getProdutos()

        binding.apply {
            recyclerViewProdutos.adapter = produtosAdapter

            btnAdicionar.setOnClickListener {
                val intent = Intent(this@MainActivity, CadastroActivity::class.java)
                startActivity(intent)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getProdutos()
    }
}
