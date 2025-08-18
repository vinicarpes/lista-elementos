package com.example.listaelementos

import android.app.Activity
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import java.util.concurrent.Executor

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // definindo o layout dessa activity ao criar o componente
        setContentView(R.layout.activity_main)
        val spn_sexo = findViewById<Spinner>(R.id.spn_sexo)
        val txt_idade = findViewById<EditText>(R.id.txt_idade)
        val txt_resultado = findViewById<TextView>(R.id.txt_resultado)
        val btn_calcular = findViewById<Button>(R.id.btn_calcular)
        spn_sexo.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listOf("Masculino", "Feminino"))

        btn_calcular.setOnClickListener{
            val sexo = spn_sexo.selectedItem as String
            val idade = txt_idade.text.toString().toInt()
            var resultado = 0
            if(sexo == "Masculino"){
                resultado = 65 - idade
            }else{
                resultado = 60 - idade
            }

            txt_resultado.text = "Faltam $resultado anos para você se aposentar."
        }
    }
}