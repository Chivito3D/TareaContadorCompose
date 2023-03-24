package com.example.tareacontadorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tareacontadorcompose.ui.theme.TareaContadorComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TareaContadorComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface{
                    ContadorEnCompose()
            }
        }
    }
}
}

const val startCountDefault = 0
const val incrementDefault = 1

@Preview(showBackground = true)
@Composable
fun ContadorEnCompose() {

    var countGlobal by remember { mutableStateOf(startCountDefault) }

    val changeCountGlobal : (Int) -> Unit = {countGlobal = it}

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Contador(countGlobal, changeCountGlobal = changeCountGlobal)
        Contador(countGlobal) { countGlobal = it }

        Row() {
            Text(text = stringResource(R.string.text_count_global))
            Text(text = "$countGlobal", Modifier.padding(5.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_delete_24),
                contentDescription = "borrar",
                modifier = Modifier.clickable { countGlobal = startCountDefault }
            )
        }
    }


}

@Composable
fun Contador(countGlobal: Int, changeCountGlobal: (Int) -> Unit) {

    val focusManager = LocalFocusManager.current

    var counter by remember { mutableStateOf(startCountDefault) }
    var incremento: Int? by remember { mutableStateOf(incrementDefault) }

    Column(

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,

            ) {
            Button(
                onClick = {
                    counter += incremento!!
                    changeCountGlobal(countGlobal + incremento!!)
                    focusManager.clearFocus()
                },
                enabled = incremento != null
            ) {
                Text(text = "Aumenta")
            }
            Spacer(Modifier.width(10.dp))
            Text(text = counter.toString())
            Spacer(Modifier.width(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_delete_24),
                contentDescription = "borrar",
                modifier = Modifier.clickable { counter = startCountDefault })
        }
        TextField(
            value = incremento?.toString() ?: "",
            onValueChange = {
                incremento = it.toIntOrNull()?.let {
                    if (it < 1 || it > 99) incrementDefault else it
                }
            },
            label = { Text(text = "Incremento") },
            placeholder = { Text(text = "Introdúcelo") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

    }


}