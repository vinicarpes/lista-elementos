package com.example.listaelementos.ui.activities

import android.R.drawable.ic_menu_camera
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.enableLiveLiterals
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listaelementos.R
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.ui.theme.AppTheme
import com.example.listaelementos.ui.theme.onPrimaryLight
import com.example.listaelementos.ui.theme.primaryLight
import com.example.listaelementos.ui.viewmodels.MainComposeViewModel
import com.example.listaelementos.ui.viewmodels.ProdutoComposeState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainComposeActivity : AppCompatActivity() {
    private val viewModel: MainComposeViewModel by viewModel<MainComposeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val produtosState by viewModel.state.collectAsState()
            AppTheme {
                ListaCompras(produtosState)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProdutos()
    }
}

@Composable
private fun Titulo(msg: String) {

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        text = msg,
        color = onPrimaryLight,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )

}

@Composable
private fun ElementoLista(produto: Produto) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(border = BorderStroke(1.dp, primaryLight))
    ) {
        Image(
            painter = painterResource(ic_menu_camera),
            contentDescription = "Ícone de câmera",
            modifier = Modifier
                .padding(start = 4.dp, end = 16.dp)
                .clip(CircleShape)
                .width(40.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(
                    text = produto.nome,
                    color = primaryLight
                )
            }
            Spacer(Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "R$" + produto.valor.toString(),
                    color = primaryLight)
                Text(
                    text = "Quantidade: " + produto.quantidade.toString(),
                    color = primaryLight
                )
            }
        }
    }
}

@Composable
private fun BotaoAdicionarProduto(context: Context) {
    Button(
        onClick = { context.startActivity(Intent(context, CadastroComposeActivity::class.java)) },
        contentPadding = PaddingValues(16.dp, 12.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryLight
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val adicionarProduto = stringResource(id = R.string.adicionar_produto)
        Text(text = adicionarProduto)
    }
}

@Composable
private fun ListaCompras(state: ProdutoComposeState) {

    LazyColumn {
        item {
            val titulo = stringResource(id = R.string.lista_compras)
            Titulo(titulo)
            BotaoAdicionarProduto(LocalContext.current)
            ValorDaCompra(state.valorTotal)
        }

        items(state.produtos) { produto ->
            ElementoLista(produto)
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
            text = "TOTAL: $valorTotal",
            color = primaryLight
        )
    }
}

@Preview
@Composable
private fun PreviewValorDaCompra() {
    ValorDaCompra(ProdutoComposeState().valorTotal)
}

@Preview
@Composable
private fun PreviewElementoLista() {
    ElementoLista(Produto("Arroz Parbolizado", 10.0, 2, null))
}

@Preview
@Composable
private fun PreviewTitulo() {
    Titulo("Lista de Compras")
}

@Preview
@Composable
private fun PreviewBotaoAdicionarProduto() {
    BotaoAdicionarProduto(LocalContext.current)
}

@Preview
@Composable
private fun PreviewListaCompras() {
    ListaCompras(
        state = ProdutoComposeState(
            listOf(Produto("Arroz Parbolizado", 10.0, 2, null)),
            valorTotal = "0.00"
        )
    )
}