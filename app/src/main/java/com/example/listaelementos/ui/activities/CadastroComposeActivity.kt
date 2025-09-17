package com.example.listaelementos.ui.activities

import android.os.Bundle
import android.R.drawable.ic_menu_camera
import android.util.Log
import android.widget.Toast
import com.example.listaelementos.R.string
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listaelementos.ui.theme.AppTheme
import com.example.listaelementos.ui.viewmodels.CadastroComposeViewModel
import com.example.listaelementos.ui.viewmodels.ProdutoFormState
import org.koin.androidx.viewmodel.ext.android.viewModel

class CadastroComposeActivity : AppCompatActivity() {
    private val viewModel: CadastroComposeViewModel by viewModel<CadastroComposeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FormularioCadastroDeProduto(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        aoSalvarComSucesso = {
                            finish()
                        }
                    )
                }
            }
        }
    }
}


@Composable
private fun FormularioCadastroDeProduto(
    modifier: Modifier,
    viewModel: CadastroComposeViewModel,
    aoSalvarComSucesso: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        val titulo = stringResource(id = string.lista_compras)
        TituloCadastro(titulo)
        IconeImagem()
        CamposDeTextoFormulario(
            state = state,
            aoAlterarNome = viewModel::aoMudarNome,
            aoAlterarQuantidade = viewModel::aoMudarQuantidade,
            aoAlterarValor = viewModel::aoMudarValor
        )
        BotaoInserirProduto(aoSalvar = {
            viewModel.valiadaParaSalvarProduct(state.nome, state.valor, state.quantidade)
            aoSalvarComSucesso()
        })
    }
}

@Composable
private fun CamposDeTextoFormulario(
    state: ProdutoFormState,
    aoAlterarNome: (String) -> Unit = {},
    aoAlterarQuantidade: (String) -> Unit = {},
    aoAlterarValor: (String) -> Unit = {}
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        label = {
            Text(
                text = stringResource(id = string.Produto_padrao),
            )
        },
        value = state.nome,
        onValueChange = aoAlterarNome
    )
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        label = {
            Text(
                text = stringResource(id = string.quantidade_padrao),
            )
        },
        value = state.quantidade,
        onValueChange = aoAlterarQuantidade,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        singleLine = true
    )
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        label = {
            Text(
                text = stringResource(id = string.valor_padrao),
            )
        },
        value = state.valor,
        onValueChange = aoAlterarValor,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        singleLine = true
    )
}


@Composable
private fun IconeImagem() {
    Image(
        painter = painterResource(ic_menu_camera),
        contentDescription = "Ícone de câmera",
        modifier = Modifier
            .padding(start = 4.dp, end = 16.dp, top = 30.dp)
            .clip(CircleShape)
            .width(100.dp)
            .height(100.dp)
            .fillMaxWidth(),
        alignment = Alignment.Center
    )
}


@Composable
private fun BotaoInserirProduto(aoSalvar: () -> Unit = {}) {
    val textoBotao = stringResource(id = string.botao_inserir_produto)
    Button(
        onClick = {
            aoSalvar()
        },
        contentPadding = PaddingValues(16.dp, 12.dp),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(text = textoBotao)
    }
}


@Composable
private fun TituloCadastro(msg: String) {
    Text(
        text = msg,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}


@Preview
@Composable
private fun PreviewFormularioCadastroDeProduto() {
    AppTheme(darkTheme = true) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier.padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val titulo = stringResource(id = string.lista_compras)
                val state = ProdutoFormState()
                TituloCadastro(titulo)
                IconeImagem()
                CamposDeTextoFormulario(
                    state = state,
                    aoAlterarNome = {},
                    aoAlterarQuantidade = {},
                    aoAlterarValor = {}
                )
                BotaoInserirProduto(aoSalvar = {
                })
            }
        }
    }
}