package com.example.listaelementos.ui.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.listaelementos.R
import com.example.listaelementos.database.database
import com.example.listaelementos.databinding.ActivityCadastroBinding
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.utils.produtosGlobal
import com.example.listaelementos.repositories.ProdutoRepository
import kotlinx.coroutines.launch

class CadastroActivity : AppCompatActivity() {
    val COD_IMAGE = 101
    var imageBitMap: Bitmap? = null

    private lateinit var binding: ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val txt_produto =binding.txtProduto
        val txt_qtd = binding.txtQuantidade
        val txt_valor = binding.txtValor
        val img_foto_produto = binding.imgFotoProduto

        binding.btnInserir.setOnClickListener {
            val produtoRepository = ProdutoRepository(database.produtoDao())
            val produto = txt_produto.text.toString()
            val valor = txt_valor.text.toString()
            val qtd = txt_qtd.text.toString()
            if ((produto.isNotEmpty() && valor.isNotEmpty() && qtd.isNotEmpty())) {
                val prod = Produto(produto, valor.toDouble(), qtd.toInt(), imageBitMap)
                produtosGlobal.add(prod)
                txt_produto.text.clear()
                txt_qtd.text.clear()
                txt_valor.text.clear()
                img_foto_produto.setImageResource(android.R.drawable.ic_menu_camera)
                lifecycleScope.launch {
                    produtoRepository.save(prod)
                }
                finish()
                Toast.makeText(this, "Produto inserido com sucesso", Toast.LENGTH_SHORT).show()
            }else{
                txt_produto.error= if(txt_produto.text.isNotEmpty()) "Preencha o produto devidamente" else null
                txt_qtd.error= if(txt_qtd.text.isNotEmpty()) "Preencha a quantidade devidamente" else null
                txt_valor.error= if(txt_valor.text.isNotEmpty()) "Preencha o valor devidamente" else null
                Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_LONG).show()
            }
        }

        img_foto_produto.setOnClickListener{
            abrirGaleria()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val img_foto_produto = binding.imgFotoProduto

        if (requestCode == COD_IMAGE && resultCode == RESULT_OK){
            if (data != null) {
                //lendo a uri da imagem selecionada
                val inputStream = contentResolver.openInputStream(data.data!!)
                //transformando o resultado em bitmap
                imageBitMap = BitmapFactory.decodeStream(inputStream)
                //exibindo a imagem na tela
                img_foto_produto.setImageBitmap(imageBitMap)
            }
        }
    }

    fun abrirGaleria(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        intent.type = "image/*"

        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_IMAGE)
    }
}