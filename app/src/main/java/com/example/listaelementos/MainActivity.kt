package com.example.listaelementos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.listaelementos.databinding.ActivityMainBinding
import com.example.listaelementos.ui.activities.CadastroActivity
import com.example.listaelementos.ui.adapters.ProdutoAdapter
import com.example.listaelementos.ui.viewmodels.MainViewModel
import com.example.listaelementos.ui.viewmodels.ProdutoState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val produtosAdapter = ProdutoAdapter(
            context = this
        )
        viewModel.getProdutos()

        binding.apply {
            recyclerViewProdutos.adapter = produtosAdapter

            btnAdicionar.setOnClickListener {
                val intent = Intent(this@MainActivity, CadastroActivity::class.java)
                startActivity(intent)
            }
        }

        viewModel.state.observe(this) { state ->
            when (state) {
                is ProdutoState.Loading -> {
                    binding.txtTotal.text = ""
                }
                is ProdutoState.Success -> {
                    produtosAdapter.setList(state.produtos)
                    binding.txtTotal.text = "TOTAL: ${viewModel.updateTotal(state.produtos)}"
                }
                is ProdutoState.Error -> {
                    binding.txtTotal.text = state.message
                }
            }
        }


    }

    override fun onResume() {
        super.onResume()
        viewModel.getProdutos()
    }
}
