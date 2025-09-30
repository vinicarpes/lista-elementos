package com.example.listaelementos.ui.activities

import android.R.drawable.ic_menu_camera
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.listaelementos.R
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.ui.components.BotaoPrimario
import com.example.listaelementos.ui.components.ListaComprasTopBar
import com.example.listaelementos.ui.theme.AppTheme
import com.example.listaelementos.ui.viewmodels.MainComposeViewModel
import com.example.listaelementos.ui.viewmodels.ProdutoComposeState
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

class MainComposeActivity : AppCompatActivity() {
    private val viewModel: MainComposeViewModel by viewModel<MainComposeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(
                    topBar = { ListaComprasTopBar(this) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    ListaCompras(viewModel = viewModel, innerPadding)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProdutos()
    }
}

@Composable
private fun ElementoLista(produto: Produto, aoRemoverProduto: (produto: Produto) -> Unit) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(border = BorderStroke(1.dp, Color.Gray))
            .clickable(enabled = true, onClick = {
                val intent = Intent(context, CadastroComposeActivity::class.java)
                intent.putExtra("produto", produto as Serializable)
                context.startActivity(intent)
            })
    ) {
        Image(
            painter = painterResource(ic_menu_camera),
            contentDescription = "Ícone de câmera",
            modifier = Modifier
                .padding(start = 4.dp)
                .clip(CircleShape)
                .width(40.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Column {
                Text(
                    text = produto.nome,
                )
            }
            Spacer(Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "R$" + produto.valor.toString()
                )
                Text(
                    text = "x" + produto.quantidade.toString(),
                )
            }
            IconButton(onClick = {
                Log.i("MainComposeActivity", "Clicou no botão de remover $produto")
                aoRemoverProduto(produto)
                Log.i("MainComposeActivity", "Deletou   $produto")
            }) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
private fun BotaoListaLojas() {
    val context = LocalContext.current
    Button(
        onClick = { context.startActivity(Intent(context, ListaLojasComposeActivity::class.java)) },
        contentPadding = PaddingValues(16.dp, 12.dp),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val listaLojas = "Lista de Lojas"
        Text(text = listaLojas)
    }
}

@Composable
fun ListaCompras(viewModel: MainComposeViewModel, innerPadding: PaddingValues) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LazyColumn(contentPadding = innerPadding) {
        item {
            BotaoPrimario(
                text = "Buscar Lojas",
                onClick = {
                    context.startActivity(
                        Intent(
                            context,
                            ListaLojasComposeActivity::class.java
                        )
                    )
                })
            BotaoPrimario(
                stringResource(R.string.adicionar_produto),
                onClick = {
                    context.startActivity(
                        Intent(
                            context,
                            CadastroComposeActivity::class.java
                        )
                    )
                },
            )
            when (val s = state) {
                is ProdutoComposeState.Loading -> {
                    ValorDaCompra("")
                }

                is ProdutoComposeState.Success -> {
                    ValorDaCompra(s.valorTotal)
                }
            }
        }
        when (state) {
            is ProdutoComposeState.Loading -> {
                item { Text("Carregando...") }
            }

            is ProdutoComposeState.Success -> {
                items((state as ProdutoComposeState.Success).produtos) { produto ->
                    ElementoLista(produto, aoRemoverProduto = {
                        viewModel.removerProduto(produto)
                    }
                    )
                }
            }

            is ProdutoComposeState.Error -> {
                val message = (state as ProdutoComposeState.Error).message
                item { Text("Erro: $message") }
            }
        }
    }
}


@Composable
private fun ValorDaCompra(valorTotal: String) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "TOTAL: $valorTotal"
        )
    }
}

@Preview
@Composable
private fun PreviewValorDaCompra() {
    ValorDaCompra("0.00")
}

@Preview
@Composable
private fun PreviewElementoLista() {
    AppTheme(darkTheme = true) {
        Scaffold { innerPadding ->
            val mod = Modifier.padding(innerPadding)
            ElementoLista(
                produto = Produto("Arroz Parbolizado", 10.0, 2, null),
                aoRemoverProduto = {})
        }
    }
}

@Preview
@Composable
private fun PreviewBotaoAdicionarProduto() {
    BotaoPrimario("Adicionar Produto", onClick = {})
}

@Preview
@Composable
private fun PreviewTopBar() {
    ListaComprasTopBar(MainComposeActivity())
}

@Preview
@Composable
private fun PreviewListaCompras() {
    AppTheme(darkTheme = true) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ListaCompras(
                viewModel = viewModel(),
                innerPadding = innerPadding
            )
        }
    }
}