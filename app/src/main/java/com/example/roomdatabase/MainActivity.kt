package com.example.roomdatabase

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomdatabase.ui.theme.RoomDatabaseTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      RoomDatabaseTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          val owner = LocalViewModelStoreOwner.current
          owner?.let {
            val viewModel : MainViewModel = viewModel(it
              ,"MainViewModel"
              , MainViewModelFactory(LocalContext.current.applicationContext as Application))
            ScreenSetup(viewModel = viewModel)
          }
        }
      }
    }
  }
}

@Composable
fun ScreenSetup(viewModel: MainViewModel){
  val allProducts by viewModel.allProducts.observeAsState(listOf())
  val searchResults by viewModel.allProducts.observeAsState(listOf())

  MainScreen(allProducts, searchResults, viewModel)

}

@Composable
fun MainScreen(allProducts:List<Product>
,searchResults: List<Product>
,viewModel: MainViewModel){
  var productName by remember{ mutableStateOf("") }
  var productQuantity by remember{ mutableStateOf("") }
  var searching by remember{ mutableStateOf(false)}
  var onProductTextChange = {text:String ->productName = text}
  var onProductQuantityChange = {text:String->productQuantity = text}
  
  Column(horizontalAlignment = Alignment.CenterHorizontally
  , modifier = Modifier.fillMaxWidth()) {

    CustomTextField(title = "Nombre del Producto"
      , textState = productName
      , onTextChange = onProductTextChange
      , keyboardType = KeyboardType.Text)

    CustomTextField(title = "Cantidad"
      , textState = productQuantity
      , onTextChange = onProductQuantityChange
      , keyboardType = KeyboardType.Number)

    Row(
      horizontalArrangement = Arrangement.SpaceEvenly
    , modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
    ) {
      Button(onClick = {
        if (productQuantity.isNotEmpty()){
          viewModel.insertProduct(Product(productName, productQuantity.toInt()))
          searching = false
        }
      }) {
        Text("Agregar")
      }
      Button(onClick = {
        searching = true
        viewModel.findProduct(productName)
      }) {
        Text("Buscar")
      }
      Button(onClick = {
        searching = false
        viewModel.deleteProduct(productName)
      }) {
        Text("Eliminar")
      }
      Button(onClick = {
        searching = false
        productName = ""
        productQuantity = ""
      }) {
        Text(text = "Limpiar")
      }
    }
  }

  LazyColumn(Modifier.fillMaxWidth().padding(10.dp)){
    val list = if(searching)searchResults else allProducts
    item{
      TitleRow(head1 = "ID", head2 = "Producto", head3 =
      "Cantidad")
    }

    items(list){
      ProductRow(id = it.id, name = it.productName,
        quantity = it.quantity)
    }
  }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(title:String
,textState:String
,onTextChange: (String) -> Unit
,keyboardType: KeyboardType){
  OutlinedTextField(value = textState
    , onValueChange = {onTextChange(it)}
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
