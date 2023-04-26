package com.example.roomdatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomdatabase.ui.theme.RoomDatabaseTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      RoomDatabaseTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          ScreenSetup()
        }
      }
    }
  }
}

@Composable
fun ScreenSetup(){

}

@Composable
fun MainScreen(){

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(title:String
,textState:String
,onTextChange: (String) -> Unit
,keyboardType: KeyboardType){
  OutlinedTextField(value = textState, onValueChange = {onTextChange(it)}
  , keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
  , singleLine = true
  , label = { Text(text = title)}
  , modifier = Modifier.padding(10.dp)
  , textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp))
}

@Composable
fun ProductRow(id:Int,name:String,quantity:Int){
  Row(modifier = Modifier
    .padding(5.dp)
    .fillMaxWidth()) {
    Text(text = id.toString(), modifier = Modifier.weight(0.1f))
    Text(text = name, modifier = Modifier.weight(0.2f))
    Text(text = quantity.toString(), modifier = Modifier.weight(0.2f))
  }
}

@Composable
fun TitleRow(head1:String,head2:String,head3:String){
  Row(
    modifier = Modifier
      .padding(5.dp)
      .fillMaxWidth()
      .background(MaterialTheme.colorScheme.onPrimary)
  ) {
    Text(head1, modifier = Modifier.weight(0.1f), color = Color.White)
    Text(head2, modifier = Modifier.weight(0.2f), color = Color.White)
    Text(head3, modifier = Modifier.weight(0.2f), color = Color.White)
  }
}
