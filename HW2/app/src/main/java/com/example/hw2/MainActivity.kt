package com.example.hw2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.hw2.ui.theme.HW2Theme

class Data: ViewModel() {
    var number by mutableStateOf("")
        private set

    fun changeNumber(number: String) {
        this.number = number
    }
}

class MainActivity : ComponentActivity() {
    private val data: Data by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HW2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Dialer(
                        textValue = data.number,
                        onClick = { data.changeNumber(data.number + it)},
                        dialOnClick = { dial(data.number)},
                        eraseOnClick = { data.changeNumber(data.number.substring(0,data.number.length - 1))}
                    )
                }
            }
        }
    }

    private fun dial(number: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$number")
        }
        if(intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}


@Composable
fun Dialer(textValue: String,onClick: (String) -> Unit,dialOnClick: () -> Unit,eraseOnClick: () -> Unit,modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            textValue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 100.dp),
            textAlign = TextAlign.Center,
            fontSize = 50.sp
        )
        Keyboard(onClick,dialOnClick,eraseOnClick)
    }
}

@Composable
fun Keyboard(onClick: (String) -> Unit,dialOnClick: () -> Unit,eraseOnClick: () -> Unit,modifier: Modifier = Modifier) {
    Column(
        modifier= modifier
    ) {
        val rowModifier = Modifier.padding(start=10.dp,end=10.dp, bottom = 20.dp)
        ButtonRow(btn1Value = "1", btn2Value = "2", btn3Value = "3", onClick = onClick,modifier = rowModifier)
        ButtonRow(btn1Value = "4", btn2Value = "5", btn3Value = "6", onClick = onClick,modifier = rowModifier)
        ButtonRow(btn1Value = "7", btn2Value = "8", btn3Value = "9", onClick = onClick,modifier = rowModifier)
        Row(modifier = rowModifier) {
            MyButton(value = stringResource(R.string.dial), onClick = { dialOnClick() },modifier=Modifier.weight(1f))
            MyButton(value = "0", onClick = onClick,modifier=Modifier.weight(1f))
            MyButton(value = stringResource(R.string.erase), onClick = { eraseOnClick() },modifier=Modifier.weight(1f))
        }
    }
}

@Composable
fun ButtonRow(btn1Value: String,btn2Value: String,btn3Value: String,onClick: (String) -> Unit,modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        MyButton(value = btn1Value, onClick = onClick,modifier=Modifier.weight(1f))
        MyButton(value = btn2Value, onClick = onClick,modifier=Modifier.weight(1f))
        MyButton(value = btn3Value, onClick = onClick,modifier=Modifier.weight(1f))
    }
}

@Composable
fun MyButton(value: String,onClick: (String) -> Unit,modifier: Modifier = Modifier) {
    Button(onClick = { onClick(value) },modifier = modifier
        .padding(start = 7.dp)
        .width(100.dp)) {
        Text(value, fontSize = 20.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DialerLayoutPreview() {
    HW2Theme {
        Dialer("999999999",{},{},{})
    }
}