package sainero.dani.intermodular.Views.Administration.Products.Types

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
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
import sainero.dani.intermodular.DataClass.Tipos
import sainero.dani.intermodular.DataClass.Users
import sainero.dani.intermodular.ViewModels.ViewModelTipos
import sainero.dani.intermodular.Views.Administration.Products.Types.ui.theme.IntermodularTheme

class ProductEditType : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntermodularTheme {

            }
        }
    }
}

@Composable
fun MainProductEditType(id: Int,viewModelTipos: ViewModelTipos) {
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }
    val result = remember { mutableStateOf("") }
    var deleteType = remember { mutableStateOf(false) }

    //Posible consulta en la Base de datos ¿? (but is ok)

    /*
    var selectedType: Tipos = Tipos(_id = id,"","")
    viewModelTipos.typeListResponse.forEach{
        if (it._id.equals(id))  selectedUser = it
    }

    var (textDniUser, onValueChangeDniUser) = rememberSaveable { mutableStateOf(selectedUser.dni) }
    var (textNameUser, onValueChangeNameUser) = rememberSaveable { mutableStateOf(selectedUser.name) }
    var (textSurnameUser, onValueChangeSurnameUser) = rememberSaveable { mutableStateOf(selectedUser.surname) }
    var (textFnacUser, onValueChangeFnacUser) = rememberSaveable { mutableStateOf(selectedUser.fnac) }
    var (textEmailUser, onValueChangeEmailUser) = rememberSaveable { mutableStateOf(selectedUser.email) }
    var (textUserUser, onValueChangeUserUser) = rememberSaveable { mutableStateOf(selectedUser.user) }
    var (textPasswordUser, onValueChangePasswordUser) = rememberSaveable { mutableStateOf(selectedUser.password) }
    var (textRolUser, onValueChangeRolUser) = rememberSaveable { mutableStateOf(selectedUser.rol) }
    var (textPhoneNumberUser, onValueChangePhoneNumberUser) = rememberSaveable { mutableStateOf(selectedUser.phoneNumber) }

    //Funciones extras a realizar
    if (deleteUser.value) {
        confirmDeleteUser(viewModelUsers = viewModelUsers,id = id)
    }

    //Ventana
    Scaffold(

        scaffoldState = scaffoldState,

        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Edición de empleado",color = Color.White)
                },
                backgroundColor = Color.Blue,
                elevation = AppBarDefaults.TopAppBarElevation,
                actions = {
                    IconButton(
                        onClick = {
                            deleteUser.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar empleado",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }

                    Box (Modifier.wrapContentSize()){
                        IconButton(onClick = {
                            expanded.value = true
                            result.value = "More icon clicked"
                        }) {
                            Icon(
                                Icons.Filled.MoreVert,
                                contentDescription = "More icon"
                            )
                        }

                        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
                            DropdownMenuItem(
                                onClick = {
                                    expanded.value = false
                                }) {
                                Text(text = "Gestionar nominas")
                            }
                        }
                    }

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

                createRowList(text = "DNI", value = textDniUser, onValueChange = onValueChangeDniUser)
                createRowList(text = "Name", value = textNameUser, onValueChange = onValueChangeNameUser)
                createRowList(text = "Surname", value = textSurnameUser, onValueChange = onValueChangeSurnameUser)
                createRowList(text = "Fecha Nacimiento", value = textFnacUser, onValueChange = onValueChangeFnacUser)
                createRowList(text = "Email", value = textEmailUser, onValueChange = onValueChangeEmailUser)
                createRowList(text = "User", value = textUserUser, onValueChange = onValueChangeUserUser)
                createRowList(text = "Contraseña", value = textPasswordUser, onValueChange = onValueChangePasswordUser)
                createRowList(text = "Rol", value = textPhoneNumberUser, onValueChange = onValueChangePhoneNumberUser)
                createRowList(text = "Rol", value = textRolUser, onValueChange = onValueChangeRolUser)

                Spacer(modifier = Modifier.padding(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(
                        onClick = {
                            textDniUser = selectedUser.dni
                            textNameUser = selectedUser.name
                            textSurnameUser = selectedUser.surname
                            textFnacUser = selectedUser.fnac
                            textEmailUser = selectedUser.email
                            textUserUser = selectedUser.user
                            textPasswordUser = selectedUser.password
                            textRolUser = selectedUser.rol
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
                            selectedUser = Users(_id = id, dni = textDniUser, name =  textNameUser, surname =  textSurnameUser, fnac =  textFnacUser, user =  textUserUser, password =  textPasswordUser, rol =  textRolUser, email =  textEmailUser, newUser = false, phoneNumber = "" )
//                            viewModelUsers.editUser(selectedUser)

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
        })*/
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview15() {
    IntermodularTheme {

    }
}