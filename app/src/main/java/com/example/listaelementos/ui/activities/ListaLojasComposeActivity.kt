package com.example.listaelementos.ui.activities

import android.R.drawable.ic_menu_camera
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.listaelementos.R
import com.example.listaelementos.dto.LojaParaListagemUi
import com.example.listaelementos.ui.theme.AppTheme
import com.example.listaelementos.ui.viewmodels.ListaLojasComposeViewModel
import com.example.listaelementos.ui.viewmodels.LojaState
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListaLojasComposeActivity : AppCompatActivity() {
    private val viewModel: ListaLojasComposeViewModel by viewModel<ListaLojasComposeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListaLojas(
                        viewModel = viewModel,
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(innerPadding)
                            .fillMaxWidth()
                            .padding(top = 40.dp)
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLojas()
    }
}

@Composable
private fun Titulo(msg: String) {

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        text = msg,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )

}

@Composable
private fun ElementoLista(loja: LojaParaListagemUi) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(border = BorderStroke(1.dp, Color.Gray))
    ) {
        Image(
            painter = painterResource(ic_menu_camera),
            contentDescription = "Ícone de câmera",
            modifier = Modifier
                .padding(start = 4.dp)
                .clip(CircleShape)
                .width(40.dp)
        )
        Text(
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            text = loja.name,
        )
    }
}

@Composable
private fun BotaoAdicionarProduto() {
    val context = LocalContext.current
    Button(
        onClick = { context.startActivity(Intent(context, CadastroComposeActivity::class.java)) },
        contentPadding = PaddingValues(16.dp, 12.dp),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val adicionarProduto = stringResource(id = R.string.adicionar_produto)
        Text(text = adicionarProduto)
    }
}

@Composable
private fun ListaLojas(viewModel: ListaLojasComposeViewModel, modifier: Modifier) {

    val state by viewModel.lojaState.collectAsState()

    LazyColumn {
        item {
            val titulo = stringResource(id = R.string.lista_compras)
            Titulo(titulo)
            BotaoAdicionarProduto()
        }
        when (state) {
            is LojaState.Loading -> {
                item {
                    Text(
                        text = "Carregando...",
                        modifier = modifier,
                        textAlign = TextAlign.Center
                    )
                }
            }

            is LojaState.Success -> {
                items((state as LojaState.Success).lojas) { loja ->
                    ElementoLista(loja)
                }
            }

            is LojaState.Error -> {
                val message = (state as LojaState.Error).message
                item { Text("Erro: $message", modifier = modifier, textAlign = TextAlign.Center) }
            }

            is LojaState.Vazio -> {
                val message = (state as LojaState.Vazio).message
                item { Text(message, modifier = modifier, textAlign = TextAlign.Center) }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewElementoLista() {
    AppTheme(darkTheme = true) {
        Scaffold { innerPadding ->
            val mod = Modifier.padding(innerPadding)
            ElementoLista(LojaParaListagemUi("Nome bacana", "Link de imagem", 10.0.toBigDecimal()))
        }
    }
}

@Preview
@Composable
private fun PreviewTitulo() {
    Titulo("Lista de Compras")
}

@Preview
@Composable
private fun PreviewBotaoAdicionarProduto() {
    BotaoAdicionarProduto()
}

@Preview
@Composable
private fun PreviewListaLojas() {
    AppTheme(darkTheme = true) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ListaLojas(
                viewModel = viewModel(),
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}