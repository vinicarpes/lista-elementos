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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    var produtos: List<Produto> = emptyList()

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val recycler_view_produtos = binding.recyclerViewProdutos
        val produtosAdapter = ProdutoAdapter(
            context = this,
            produtos = produtos
        )

        recycler_view_produtos.adapter = produtosAdapter

        val btn_adicionar = binding.btnAdicionar
        btn_adicionar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val repository = ProdutoRepository(database.produtoDao())
        val recycler_view_produtos = binding.recyclerViewProdutos
        val txt_total = binding.txtTotal
        val adapter = recycler_view_produtos.adapter as ProdutoAdapter
        lifecycleScope.launch {
            produtos = withContext(Dispatchers.IO) {
                repository.produtos.map { it.toProduto() }
            }

            adapter.updateData(produtos)

            val soma = produtos.sumOf { it.valor * it.quantidade }
            val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            txt_total.text = "TOTAL: ${f.format(soma)}"
        }
    }
}