package com.example.listaelementos

import android.os.Bundle
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
            }
        }

        list_view_produtos.adapter = produtosAdapter
    }

}