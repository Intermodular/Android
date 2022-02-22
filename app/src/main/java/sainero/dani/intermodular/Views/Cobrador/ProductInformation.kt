package sainero.dani.intermodular.Views.Cobrador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelProductos

class ProductInformation : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainProductInformation(
    viewModelProductos: ViewModelProductos,
    productId: Int
) {
    var product: Productos = Productos(0,"","", arrayListOf(),0f, arrayListOf(),"",0)
    viewModelProductos.productListResponse.forEach { if (it._id.equals(productId)) product = it }
    Scaffold(
         topBar = {
             TopAppBar(
               title = {
                   Text(text = "Información del producto")
               },
               navigationIcon = {
                   IconButton(
                       onClick = {
                           navController.popBackStack()
                       }
                   ) {
                       Icon(
                           Icons.Filled.ArrowBack,
                           contentDescription = "More icon",
                           tint = Color.White
                       )
                   }
               }
             )
         },
        content = {
            LazyColumn(
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                item {
                    Spacer(modifier = Modifier.padding(5.dp))
                    Box(
                        modifier = Modifier.height(200.dp)
                    ) {
                        Image(
                            painter =  rememberImagePainter(
                                data =  if (!product.img.equals("")) "${product.img}" else "https://www.chollosocial.com/media/data/2019/11/678gf34.png",
                                builder = {
                                    //transformations(CircleCropTransformation())

                                }
                            ),
                            contentDescription ="Imágen del producto",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Box(
                        modifier = Modifier.height(50.dp)
                    ) {
                        Text(
                            text = product.name,
                            fontSize = 30.sp,
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center
                        )
                    }

                    Box(
                        modifier = Modifier.height(50.dp)
                    ) {
                        Text(
                            text = "Precio: ${product.price}€",
                            fontSize = 20.sp,
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center
                        )
                    }

                    Box(
                        modifier = Modifier.height(50.dp)
                    ) {
                        Text(
                            text = "Stock: ${product.stock}",
                            fontSize = 20.sp,
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                item {
                    Text(
                        text = "Ingredientes:",
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                product.ingredients.forEach {
                    item {
                        Text(
                            text = it,
                            fontSize = 20.sp,
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = "Especificaciones:",
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                product.especifications.forEach {
                    item {
                        Text(
                            text = it,
                            fontSize = 20.sp,
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                item { 
                    Spacer(modifier = Modifier.padding(20.dp))
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview23() {

}