package com.example.calculadordegorjeta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadordegorjeta.ui.theme.CalculadorDeGorjetaTheme
import java.text.NumberFormat
import kotlin.jvm.internal.Intrinsics.Kotlin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadorDeGorjetaTheme {
                AppCalculadorDeGorjeta()
            }
        }
    }
}
@Preview(showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCalculadorDeGorjeta(){
    var totalConta by remember { mutableStateOf("") }
    var porcentagemGorjeta by remember { mutableStateOf("") }
    var arredondar by remember { mutableStateOf(false) }
    val valorGorjeta = calcularGorjeta(
        totalConta.toDoubleOrNull()?:0.0,
        porcentagemGorjeta.toDoubleOrNull()?:15.0,
        arredondar
    )
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.LightGray
                ),
                title = {
                    Text(
                        text = stringResource(R.string.titulo_app),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },navigationIcon = {
                    IconButton(
                        onClick = { /*  coloque a ação de clique */ }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /* coloque a ação de clique */ }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null
                        )
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.LightGray,
                actions = {
                    IconButton(
                        onClick = { /* coloque a ação de clique */ }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = { /* coloque a ação de clique */ }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = null,
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    onClick = { /*coloque a ação de clique*/ },
                    containerColor = Color.LightGray,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        }
    ) {
            innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CampoDeTexto(
                value = totalConta,
                onValueChange = {totalConta=it},
                idTexto = R.string.valor_da_conta,
                imeAction = ImeAction.Next,
                idIcone = R.drawable.valor_conta
            )
            CampoDeTexto(
                value = porcentagemGorjeta,
                onValueChange = {porcentagemGorjeta=it},
                idTexto = R.string.porcentagem_gorjeta,
                imeAction = ImeAction.Done,
                idIcone = R.drawable.porcentagem_gorjeta
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
            ) {
                Text(text = stringResource(R.string.arredondar_gorjeta))
                Switch(
                    checked = arredondar,
                    onCheckedChange ={arredondar=it}
                )
            }
            Text(
                text = stringResource(R.string.valor_gorjeta, valorGorjeta),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
fun calcularGorjeta(
    totalConta:Double,
    porcentagemGorjeta:Double,
    arredondar:Boolean
):String{
    var gorjeta = porcentagemGorjeta / 100 * totalConta
    if(arredondar){
        gorjeta = kotlin.math.ceil(gorjeta);
    }
    return NumberFormat.getCurrencyInstance().format(gorjeta)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoDeTexto(
    value:String,
    onValueChange:(String)->Unit,
    idTexto:Int,
    imeAction:ImeAction,
    idIcone:Int
){
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = {
            Text(text = stringResource(idTexto))
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Decimal,
            imeAction = imeAction
        ),
        leadingIcon = {
            Icon(painter = painterResource(id = idIcone),
                contentDescription = null
            )
        }
    )
    Spacer( modifier = Modifier.size(10.dp))
}
