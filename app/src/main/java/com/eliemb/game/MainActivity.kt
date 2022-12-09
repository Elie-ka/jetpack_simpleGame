package com.eliemb.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eliemb.game.ui.theme.GameTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    GameScreen()
                }
            }
      }
    }
}

@Composable
fun GameScreen(){
    val number = 1..12

    var min = 1
    var max = 12

    var generatedNumber by remember {
        mutableStateOf(Random.nextInt(min,max))
    }
    var message by remember {
        mutableStateOf("")
    }
    var ans by remember {
        mutableStateOf("")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier =Modifier.fillMaxWidth() ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .weight(3f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = "Guess the value of X", fontSize = 30.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = ans.ifEmpty { "X" }, fontSize = 48.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = message, fontSize = 20.sp, color = Color.Red, textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.height(40.dp))
            LazyVerticalGrid(columns = GridCells.Fixed(3), content ={

                items(number.toList()){
                        number ->
                    Box(modifier = Modifier,
                        contentAlignment = Alignment.Center){
                        Button(onClick = {
                            message = guessGame(providedNumber = number,
                                generatedNumber = generatedNumber,
                                min = min, max = max)

                            if (message.contains("oura", ignoreCase = true)){
                                ans = number.toString()
                            }
                        },
                            modifier = Modifier.padding(10.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFDABEFC))) {
                            Text(text = number.toString(),
                                modifier = Modifier.padding(15.dp), color = Color.White, fontSize = 20.sp)
                        }
                    }
                }

            } , modifier = Modifier
                .weight(7f)
                .fillMaxSize())
        }


    }

    if (ans.isEmpty()){
        DialogView(message){
            message = ""
            ans = ""
            generatedNumber = Random.nextInt(min,max)
        }
    }
}
@Composable
fun DialogView(message:String,resetGame:()->Unit){

    var showDialog by remember {
        mutableStateOf(true)
    }
    if (showDialog){
        AlertDialog(onDismissRequest = { /*TODO*/ },
        title = { Text(text = "You won")},
        text = { Text(text = message)}, //ici a verifier
        confirmButton = {
            Button(onClick = {
                resetGame.invoke()
                showDialog = false
            }) {
                Text(text = "Replay")
            }
        })
    }
}

fun guessGame(providedNumber:Int,generatedNumber:Int,min:Int,max:Int): String{

    while (generatedNumber != providedNumber){

        return if (providedNumber < min || providedNumber > max){
             "the number is not in the interval of $min & $max"
        }else{
            if (providedNumber > generatedNumber){
               "the provided number is greater than generated number"
            }else{
                "the provided number is less than generated number"
            }
        }
    }
    return "Ouraa!!! you won, congratulation"
}