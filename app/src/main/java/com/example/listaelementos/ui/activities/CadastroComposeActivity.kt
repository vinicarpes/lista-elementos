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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.ui.theme.AppTheme
import com.example.listaelementos.ui.viewmodels.CadastroComposeViewModel
import com.example.listaelementos.ui.viewmodels.ProdutoFormState
import org.koin.androidx.viewmodel.ext.android.viewModel

class CadastroComposeActivity : AppCompatActivity() {
    private val viewModel: CadastroComposeViewModel by viewModel<CadastroComposeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val produto = intent.getParcelableExtra<Produto>("produto")
        Log.d("CadastroProduto", "produto recebido: $produto")
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FormularioCadastroDeProduto(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        aoSalvarComSucesso = {
                            finish()
                        },
                        produto
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
    aoSalvarComSucesso: () -> Unit,
    produto: Produto?
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(produto) {
        produto?.let { p ->
            viewModel.aoMudarNome(p.nome)
            viewModel.aoMudarQuantidade(p.quantidade.toString())
            viewModel.aoMudarValor(p.valor.toString())
            viewModel.aoMudarId(p.id)
        }
    }

    Column(
        modifier = Modifier.padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val titulo = stringResource(id = string.lista_compras)
        TituloCadastro(titulo)
        IconeImagem()
        CamposDeTextoFormulario(
            state = state,
            aoAlterarNome = viewModel::aoMudarNome,
            aoAlterarQuantidade = viewModel::aoMudarQuantidade,
            aoAlterarValor = viewModel::aoMudarValor,
        )
        BotaoInserirProduto(aoSalvar = {
            viewModel.valiadaParaSalvarProduct(state.nome, state.valor, state.quantidade, state.id)
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
    val modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    val tecladoNumerico = KeyboardOptions(
        keyboardType = KeyboardType.Number
    )

    CampoDeTextoFormulario(
        modifier = modifier,
        value = state.nome,
        aoMudarValor = aoAlterarNome,
        label = stringResource(id = string.Produto_padrao)
    )
    CampoDeTextoFormulario(
        modifier = modifier,
        value = state.quantidade,
        aoMudarValor = aoAlterarQuantidade,
        label = stringResource(id = string.quantidade_padrao),
        keyboardOptions = tecladoNumerico
    )
    CampoDeTextoFormulario(
        modifier = modifier,
        value = state.valor,
        aoMudarValor = aoAlterarValor,
        label = stringResource(id = string.valor_padrao),
        keyboardOptions = tecladoNumerico
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

@Composable
fun CampoDeTextoFormulario(
    modifier: Modifier = Modifier,
    value: String,
    aoMudarValor: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        modifier = modifier,
        label = {
            Text(
                text = label,
            )
        },
        value = value,
        onValueChange = aoMudarValor,
        keyboardOptions = keyboardOptions,
        singleLine = true
    )
}


@Preview
@Composable
private fun PreviewFormularioCadastroDeProduto() {
    AppTheme(darkTheme = true) {
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
                aoAlterarValor = {},
            )
            BotaoInserirProduto(aoSalvar = {
            })
        }
    }
}
