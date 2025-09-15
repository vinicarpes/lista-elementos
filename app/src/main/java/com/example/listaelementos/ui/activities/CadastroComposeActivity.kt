package com.example.listaelementos.ui.activities

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listaelementos.repositories.ProdutoRepository
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
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}


@Composable
private fun FormularioCadastroDeProduto(
    modifier: Modifier,
    viewModel: CadastroComposeViewModel
) {
    val state by viewModel.state.collectAsState()
    Column(modifier = Modifier.padding(top = 40.dp)) {
        TituloCadastro("Cadastro de Produto")
        IconeImagem()
        CamposDeTextoFormulario(
            state = state,
            aoAlterarNome = { novoNome ->
                viewModel.aoMudarNome(novoNome)
            },
            aoAlterarQuantidade = { novaQuantidade ->
                viewModel.aoMudarQuantidade(novaQuantidade)
            },
            aoAlterarValor = { novoValor ->
                viewModel.aoMudarValor(novoValor)
            }
        )
        BotaoInserirProduto(aoSalvar = {
            viewModel.valiadaParaSalvarProduct(state.nome, state.valor, state.quantidade)
        })
    }
}

@Composable
private fun CamposDeTextoFormulario(
    state: ProdutoFormState,
    aoAlterarNome: (String) -> Unit = {},
    aoAlterarQuantidade : (String) -> Unit = {},
    aoAlterarValor : (String) -> Unit = {}
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        label = {
            Text(
                text = "Produto",
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
                text = "Quantidade",
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
                text = "R$0.00",
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_menu_camera),
            contentDescription = "Ícone de câmera",
            modifier = Modifier
                .padding(start = 4.dp, end = 16.dp)
                .clip(CircleShape)
                .width(100.dp)
                .height(100.dp)
        )
    }
}

@Composable
private fun BotaoInserirProduto(aoSalvar: () -> Unit = {}) {
    Row {
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
            Text(text = "Inserir")
        }
    }
}

@Composable
private fun TituloCadastro(msg: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = msg,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun PreviewFormularioCadastroDeProduto() {
    AppTheme(darkTheme = true) {
//        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//            FormularioCadastroDeProduto(
//                modifier = Modifier.padding(innerPadding),
//            )
//        }
    }
}