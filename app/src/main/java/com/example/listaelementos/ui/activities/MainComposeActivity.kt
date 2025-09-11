package com.example.listaelementos.ui.activities

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.ui.viewmodels.MainComposeViewModel
import com.example.listaelementos.ui.viewmodels.ProdutoComposeState
import com.example.listaelementos.ui.viewmodels.ValorTotalProdutoState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainComposeActivity : AppCompatActivity() {
    private val viewModel: MainComposeViewModel by viewModel<MainComposeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val produtosState by viewModel.state.collectAsState()
            val valorTotalProdutoState by viewModel.valorTotal.collectAsState()
            Log.e("MainComposeActivity", "onCreate: $produtosState")
            MaterialTheme {
                ListaCompras(produtosState, valorTotalProdutoState)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProdutos()
    }
}

@Composable
fun Titulo(msg: String) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = msg,
            color = Color(0xFFFFFFFF),
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun ElementoLista(produto: Produto) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.ic_menu_camera),
            contentDescription = "Ícone de câmera",
            modifier = Modifier
                .padding(start = 4.dp, end = 16.dp)
                .clip(CircleShape)
                .width(40.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(text = produto.nome, color = Color(0xFFFFFFFF))
            }
            Spacer(Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "R$" + produto.valor.toString(), color = Color(0xFFFFFFFF))
                Text(
                    text = "Quantidade: " + produto.quantidade.toString(),
                    color = Color(0xFFFFFFFF)
                )
            }
        }
    }
}

@Composable
fun BotaoAdicionarProduto(context : Context) {
    Button(
        onClick = { context.startActivity(Intent(context, CadastroActivity::class.java)) },
        contentPadding = PaddingValues(16.dp, 12.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6200EE)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Adicionar produto")
    }
}

@Composable
fun ListaCompras(state: ProdutoComposeState, valorTotalProdutoState: ValorTotalProdutoState) {

    LazyColumn {
        item {
            Titulo("Lista de Compras")
            BotaoAdicionarProduto(LocalContext.current)
            ValorDaCompra(valorTotalProdutoState.valorTotal)
        }

        items(state.produtos) { produto ->
            ElementoLista(produto)
        }
    }
}


@Composable
fun ValorDaCompra(valorTotal : String) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(text = "TOTAL: $valorTotal",
            color = Color(0xFFFFFFFF)
            )
    }
}

@Preview
@Composable
private fun PreviewValorDaCompra() {
    ValorDaCompra(ValorTotalProdutoState().valorTotal)
}

@Preview
@Composable
fun PreviewElementoLista() {
    ElementoLista(Produto("Arroz Parbolizado", 10.0, 2, null))
}

@Preview
@Composable
fun PreviewTitulo() {
    Titulo("Lista de Compras")
}

@Preview
@Composable
fun PreviewBotaoAdicionarProduto() {
    BotaoAdicionarProduto(LocalContext.current)
}

@Preview
@Composable
fun PreviewListaCompras() {
    ListaCompras(state = ProdutoComposeState(listOf(Produto("Arroz Parbolizado", 10.0, 2, null))), valorTotalProdutoState = ValorTotalProdutoState("0.00"))
}