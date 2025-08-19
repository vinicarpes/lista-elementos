package com.example.listaelementos

import android.content.Intent
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
        val list_view_produtos = findViewById<ListView>(R.id.list_view_produtos)

        val produtosAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)

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

}