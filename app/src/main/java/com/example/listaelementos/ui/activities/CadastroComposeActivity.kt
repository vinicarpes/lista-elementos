package com.example.listaelementos.ui.activities

import android.R
import android.os.Bundle
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
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listaelementos.ui.theme.AppTheme
import com.example.listaelementos.ui.viewmodels.CadastroComposeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CadastroComposeActivity : AppCompatActivity() {
    private val viewModel: CadastroComposeViewModel by viewModel<CadastroComposeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                FormularioCadastroDeProduto()
            }
        }
    }
}

@Composable
private fun FormularioCadastroDeProduto() {
    Column(modifier = Modifier.padding(top = 40.dp)) {
        TituloCadastro("Cadastro de Produto")
        IconeImagem()
        CampoDeTextoFormulario("Nome do produto")
        CampoDeTextoFormulario("Quantidade")
        CampoDeTextoFormulario("R$0.00")
        BotaoInserirProduto(onClickListener = {})
    }
}

@Composable
private fun CampoDeTextoFormulario(campo: String) {
    var valorPreenchido by rememberSaveable { mutableStateOf("") }
    Row {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            placeholder = {
                Text(
                    text = campo,
                    color = Color.Gray,
                )
            },
            value = valorPreenchido,
            onValueChange = { valorPreenchido = it },
        )
    }
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
private fun BotaoInserirProduto(onClickListener: () -> Unit = {}) {
    Row {
        Button(
            onClick = { },
            contentPadding = PaddingValues(16.dp, 12.dp),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            FormularioCadastroDeProduto()
        }
    }
}