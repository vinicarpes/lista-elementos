package com.example.listaelementos.ui.activities

import android.R.drawable.ic_menu_camera
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listaelementos.R.string
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.ui.theme.AppTheme
import com.example.listaelementos.ui.viewmodels.CadastroComposeViewModel
import com.example.listaelementos.ui.viewmodels.ProdutoFormState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CadastroComposeActivity : AppCompatActivity() {
    private val viewModel: CadastroComposeViewModel by viewModel<CadastroComposeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val produto = intent.getParcelableExtra<Produto>(NOME_PRODUTO)
        Log.d("CadastroProduto", "produto recebido: $produto")
        setContent {
            AppTheme {
                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) { innerPadding ->
                    FormularioCadastroDeProduto(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        exibirMensagemRetornada = { mensagem ->
                            scope.launch {
                                 snackbarHostState
                                    .showSnackbar(
                                        message = mensagem,
                                        actionLabel = "Fechar",
                                        duration = SnackbarDuration.Indefinite
                                    )
                            }
                        },
                        context = this,
                        produto = produto
                    )
                }
            }
        }
    }

    companion object {
        const val NOME_PRODUTO = "produto"
    }

}


@Composable
private fun FormularioCadastroDeProduto(
    modifier: Modifier,
    context: CadastroComposeActivity,
    viewModel: CadastroComposeViewModel,
    exibirMensagemRetornada: (String) -> Unit,
    produto: Produto?
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(produto) {
        viewModel.atualizaValoresProdutoState(produto)
    }

    LaunchedEffect(state.mensagemSucesso, state.mensagemErro) {
        state.mensagemSucesso?.let {
            exibirMensagemRetornada(it)
            viewModel.limparMensagem()
        }
        state.mensagemErro?.let {
            exibirMensagemRetornada(it)
            viewModel.limparMensagem()
        }
    }

    Column(
        modifier = Modifier.padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TituloCadastro(stringResource(string.lista_compras))
        IconeImagem()
        CamposDeTextoFormulario(
            state = state,
            aoAlterarNome = viewModel::aoMudarNome,
            aoAlterarQuantidade = viewModel::aoMudarQuantidade,
            aoAlterarValor = viewModel::aoMudarValor,
        )
        BotaoInserirProduto(aoSalvar = {
            viewModel.valiadaParaSalvarProduct{ sucesso ->
                if(sucesso) context.finish()
            }
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
        onClick = aoSalvar,
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
