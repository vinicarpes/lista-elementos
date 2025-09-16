package com.example.listaelementos.ui.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listaelementos.databinding.ActivityCadastroBinding
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.ui.viewmodels.CadastroViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CadastroActivity : AppCompatActivity() {
    val COD_IMAGE = 101
    var imageBitMap: Bitmap? = null
    private val viewModel : CadastroViewModel by viewModel<CadastroViewModel>()

    companion object {
        const val NOME_PRODUTO_DEEP_LINK = "produto"
        const val VALOR_PRODUTO_DEEP_LINK = "valor"
        const val QUANTIDADE_PRODUTO_DEEP_LINK = "quantidade"
    }

    private lateinit var binding: ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getDeepLink()

        viewModel.salvoComSucesso.observe(this) { succes ->
            if(succes){
                Toast.makeText(this, "Produto inserido com sucesso", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        viewModel.mensagemToast.observe(this) { message ->
            message?.let{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.aoExibirToast()
            }
        }

        binding.apply {
            btnInserir.setOnClickListener {
                val nome = txtProduto.text.toString()
                val valor = txtValor.text.toString()
                val qtd = txtQuantidade.text.toString()

                try{
                    viewModel.valiadaParaSalvarProduct(Produto(nome, valor.toDouble(), qtd.toInt(), imageBitMap))
                } catch (e: RuntimeException){
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
                val inputStream = contentResolver.openInputStream(data.data!!)
                imageBitMap = BitmapFactory.decodeStream(inputStream)
                binding.imgFotoProduto.setImageBitmap(imageBitMap)
            }
        }
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        intent.type = "image/*"

        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_IMAGE)
    }

    private fun getDeepLink(){
        val appLinkIntent = intent
        val appLinkData = appLinkIntent.data

        val nomeProduto = appLinkData?.getQueryParameter(NOME_PRODUTO_DEEP_LINK) ?: ""
        val valorProduto = appLinkData?.getQueryParameter(VALOR_PRODUTO_DEEP_LINK) ?: ""
        val quantidadeProduto = appLinkData?.getQueryParameter(QUANTIDADE_PRODUTO_DEEP_LINK) ?: ""

        binding.apply {
            txtProduto.setText(nomeProduto)
            txtValor.setText(valorProduto)
            txtQuantidade.setText(quantidadeProduto)
        }
    }
}