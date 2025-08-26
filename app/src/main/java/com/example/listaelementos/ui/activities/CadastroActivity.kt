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
import com.example.listaelementos.ui.viewmodels.CadastroViewModel
import com.example.listaelementos.ui.viewmodels.MainViewModel
import kotlinx.coroutines.launch

class CadastroActivity : AppCompatActivity() {
    val COD_IMAGE = 101
    var imageBitMap: Bitmap? = null

    private lateinit var viewModel: CadastroViewModel

    private lateinit var binding: ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = CadastroViewModel(this.application)

        viewModel.saveSuccessEvent.observe(this) { succes ->
            if(succes){
                Toast.makeText(this, "Produto inserido com sucesso", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        viewModel.toastMessage.observe(this) { message ->
            message?.let{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }

        binding.apply {
            btnInserir.setOnClickListener {
                val nome = txtProduto.text.toString()
                val valor = txtValor.text.toString()
                val qtd = txtQuantidade.text.toString()

                try{
                    viewModel.valiadateToSaveProduct(Produto(nome, valor.toDouble(), qtd.toInt(), imageBitMap))
                } catch (e: RuntimeException){ // excecao generica, apenas para fins didaticos
                    Toast.makeText(this@CadastroActivity, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
                }
            }


            imgFotoProduto.setOnClickListener {
                abrirGaleria()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == COD_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                //lendo a uri da imagem selecionada
                val inputStream = contentResolver.openInputStream(data.data!!)
                //transformando o resultado em bitmap
                imageBitMap = BitmapFactory.decodeStream(inputStream)
                //exibindo a imagem na tela
                binding.imgFotoProduto.setImageBitmap(imageBitMap)
            }
        }
    }

    fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        intent.type = "image/*"

        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_IMAGE)
    }
}