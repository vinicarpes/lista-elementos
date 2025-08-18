package com.example.listaelementos

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val txt_produto = findViewById<EditText>(R.id.txt_produto)
        val list_view_produtos = findViewById<ListView>(R.id.list_view_produtos)
        val btn_inserir = findViewById<Button>(R.id.btn_inserir)
        val produtosAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        btn_inserir.setOnClickListener {
            val produto = txt_produto.text.toString()
            if (produto.isNotBlank()) {
                produtosAdapter.add(produto)
                txt_produto.text.clear()
            }else{
                txt_produto.error= "Preencha o produto devidamente"
            }
        }

        list_view_produtos.adapter = produtosAdapter
        list_view_produtos.setOnItemLongClickListener{ adapterView : AdapterView<*>, view: View, position: Int, id: Long ->
            val item = produtosAdapter.getItem(position)
            produtosAdapter.remove(item)
            true
        }
    }

}