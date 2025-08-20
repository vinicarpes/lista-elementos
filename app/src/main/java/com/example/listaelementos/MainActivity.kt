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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list_view_produtos = findViewById<ListView>(R.id.list_view_produtos)
        val produtosAdapter = ProdutoAdapter(this)

        list_view_produtos.adapter = produtosAdapter
        list_view_produtos.setOnItemLongClickListener { adapterView: AdapterView<*>, view: View, position: Int, id: Long ->
            val item = produtosAdapter.getItem(position)
            produtosAdapter.remove(item)
            true
        }
        val btn_adicionar = findViewById<Button>(R.id.btn_adicionar)
        btn_adicionar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val repository = ProdutoRepository(database.produtoDao())
        val list_view_produtos = findViewById<ListView>(R.id.list_view_produtos)
        val txt_total = findViewById<TextView>(R.id.txt_total)
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