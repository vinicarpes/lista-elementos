package com.example.listaelementos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val txt_produto =findViewById<EditText>(R.id.txt_produto)
        val txt_qtd = findViewById<EditText>(R.id.txt_quantidade)
        val txt_valor = findViewById<EditText>(R.id.txt_valor)
        val btn_inserir = findViewById<Button>(R.id.btn_inserir)

        btn_inserir.setOnClickListener {
            val produto = txt_produto.text.toString()
            val valor = txt_valor.text.toString()
            val qtd = txt_qtd.text.toString()
            if (!(produto.isEmpty() && valor.isEmpty() && qtd.isEmpty())) {
                val prod = Produto(produto, valor.toDouble(), qtd.toInt())
                produtosGlobal.add(prod)
                txt_produto.text.clear()
                txt_qtd.text.clear()
                txt_valor.text.clear()
            }else{
                txt_produto.error= if(txt_produto.text.isNotEmpty()) "Preencha o produto devidamente" else null
                txt_qtd.error= if(txt_qtd.text.isNotEmpty()) "Preencha a quantidade devidamente" else null
                txt_valor.error= if(txt_valor.text.isNotEmpty()) "Preencha o valor devidamente" else null
            }
        }
    }
}