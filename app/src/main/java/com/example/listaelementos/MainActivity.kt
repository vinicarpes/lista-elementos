package com.example.listaelementos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.listaelementos.database.database
import com.example.listaelementos.databinding.ActivityMainBinding
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

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val list_view_produtos = binding.listViewProdutos
        val produtosAdapter = ProdutoAdapter(this)

        list_view_produtos.adapter = produtosAdapter
        list_view_produtos.setOnItemLongClickListener { adapterView: AdapterView<*>, view: View, position: Int, id: Long ->
            val item = produtosAdapter.getItem(position)
            produtosAdapter.remove(item)
            true
        }
        val btn_adicionar = binding.btnAdicionar
        btn_adicionar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val repository = ProdutoRepository(database.produtoDao())
        val list_view_produtos = binding.listViewProdutos
        val txt_total = binding.txtTotal
        val adapter = list_view_produtos.adapter as ProdutoAdapter
        lifecycleScope.launch {

            val produtos = withContext(Dispatchers.IO) {
                repository.produtos
            }

            adapter.clear()
            adapter.addAll(produtos.map { it.toProduto() })

            val soma = produtos.sumOf { it.valor * it.quantidade }
            val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            txt_total.text = "TOTAL: ${f.format(soma)}"
        }
    }
}