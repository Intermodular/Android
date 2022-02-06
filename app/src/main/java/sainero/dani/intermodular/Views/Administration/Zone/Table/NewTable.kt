package sainero.dani.intermodular.Views.Administration.Zone.Table

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sainero.dani.intermodular.DataClass.Mesas
import sainero.dani.intermodular.DataClass.Zonas
import sainero.dani.intermodular.ViewModels.ViewModelMesas
import sainero.dani.intermodular.Views.Administration.Zone.Table.ui.theme.IntermodularTheme

class NewTable : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainNewTable(viewModelMesas: ViewModelMesas){
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }

    //Textos
    var (textName, onValueChangeName) = rememberSaveable{ mutableStateOf("") }
    var (textNºsillas, onValueChangeNºsillas) = rememberSaveable{ mutableStateOf("") }
    var (textNumero, onValueChangeNumero) = rememberSaveable{ mutableStateOf("") }
    var (textEstado, onValueChangeEstado) = rememberSaveable{ mutableStateOf("") }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Nueva Mesa",color = Color.White)
                },
                backgroundColor = Color.Blue,
                elevation = AppBarDefaults.TopAppBarElevation,
                actions = {

                }
            )

        },
        content = {
            Spacer(modifier = Modifier.padding(10.dp))
            Column(
                Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth()
            ) {

                sainero.dani.intermodular.Views.Administration.Zone.Table.createRowList(text = "Nombre", value = textName, onValueChange = onValueChangeName, false, true)
                sainero.dani.intermodular.Views.Administration.Zone.Table.createRowList(text = "NºSillas", value = textNºsillas, onValueChange = onValueChangeNºsillas, true,true)
                sainero.dani.intermodular.Views.Administration.Zone.Table.createRowList(text = "Estado", value = textEstado, onValueChange = onValueChangeEstado, false, true)
                sainero.dani.intermodular.Views.Administration.Zone.Table.createRowList(text = "Numero", value = textNumero, onValueChange = onValueChangeNumero, true, true)
                Spacer(modifier = Modifier.padding(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(
                        onClick = {
                            textName = ""
                            textNºsillas = ""
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Color.Blue
                        ),
                        contentPadding = PaddingValues(
                            start = 10.dp,
                            top = 6.dp,
                            end = 10.dp,
                            bottom = 6.dp
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp, end = 20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Revertir cambios",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                        Text(text = "Revertir cambios", fontSize = 15.sp)
                    }

                    Button(
                        onClick = {
                            //Guardar mesa en la BD
                            val mesa: Mesas
                            if (textNºsillas.equals("") || textNumero.equals(""))
                                mesa = Mesas(0,textName,0,textEstado,0)
                            else
                                mesa = Mesas(0,textName,textNºsillas.toInt(),textEstado,textNumero.toInt())
                            viewModelMesas.uploadMesa(mesa)
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Color.Blue
                        ),
                        contentPadding = PaddingValues(
                            start = 10.dp,
                            top = 6.dp,
                            end = 10.dp,
                            bottom = 6.dp
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp, end = 20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Guardar cambios",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                        Text(text = "Guardar cambios", fontSize = 15.sp)
                    }

                }
            }

        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview18() {

}