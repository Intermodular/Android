package sainero.dani.intermodular.Views.Administration.Employee

import android.os.Bundle
import android.os.IInterface
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sainero.dani.intermodular.DataClass.Users
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.ViewModels.ViewModelUsers
import sainero.dani.intermodular.Views.Administration.Employee.ui.theme.IntermodularTheme


@ExperimentalFoundationApi
class NewEmployee : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntermodularTheme {

            }
        }
    }
}

@Composable
fun MainNewEmployee(viewModelUsers: ViewModelUsers) {

    var (textDniUser, onValueChangeDniUser) = rememberSaveable { mutableStateOf("") }
    var (textNameUser, onValueChangeNameUser) = rememberSaveable { mutableStateOf("") }
    var (textTelUser, onValueChangeTelUser) = rememberSaveable { mutableStateOf("") }
    var (textSurnameUser, onValueChangeSurnameUser) = rememberSaveable { mutableStateOf("") }
    var (textFnacUser, onValueChangeFnacUser) = rememberSaveable { mutableStateOf("") }
    var (textEmailUser, onValueChangeEmailUser) = rememberSaveable { mutableStateOf("") }
    var (textUserUser, onValueChangeUserUser) = rememberSaveable { mutableStateOf("") }
    var (textPasswordUser, onValueChangePasswordUser) = rememberSaveable { mutableStateOf("") }
    var (textRolUser, onValueChangeRolUser) = rememberSaveable { mutableStateOf("") }

    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }
    val showToast = remember { mutableStateOf(false) }
    val context = LocalContext.current

    val newEmployee: Users = Users(0,textNameUser,textSurnameUser,textDniUser,textTelUser,textFnacUser,textUserUser,textPasswordUser,textRolUser,textEmailUser)


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Nuevo empleado",color = Color.White)
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
                createRowList(text = "DNI", value = textDniUser, onValueChange = onValueChangeDniUser)
                createRowList(text = "Name", value = textNameUser, onValueChange = onValueChangeNameUser)
                createRowList(text = "Surname", value = textSurnameUser, onValueChange = onValueChangeSurnameUser)
                createRowList(text = "Fecha Nacimiento", value = textFnacUser, onValueChange = onValueChangeFnacUser)
                createRowList(text = "Email", value = textEmailUser, onValueChange = onValueChangeEmailUser)
                createRowList(text = "User", value = textUserUser, onValueChange = onValueChangeUserUser)
                createRowList(text = "Contraseña", value = textPasswordUser, onValueChange = onValueChangePasswordUser)
                createRowList(text = "Rol", value = textRolUser, onValueChange = onValueChangeRolUser)


                Spacer(modifier = Modifier.padding(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(
                        onClick = {

                            textNameUser = ""
                            textSurnameUser = ""
                            textDniUser = ""
                            textTelUser = ""
                            textFnacUser = ""
                            textUserUser = ""
                            textPasswordUser
                            textRolUser = ""
                            textEmailUser = ""

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

                            viewModelUsers.uploadUser(newEmployee)
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
    if(showToast.value) {
        ToastDemo("")
    }
}


@Composable
private fun createRowList(text: String, value: String, onValueChange: (String) -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(text = "${text}:", Modifier.width(100.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            placeholder = { Text(text) },
            label = { Text(text = text) },
            modifier = Modifier
                .padding(start = 10.dp, end = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview13() {
    IntermodularTheme {
      //  MainNewEmployee()
    }
}


@Composable
fun ToastDemo(message: String) {
    Toast.makeText(LocalContext.current,message,Toast.LENGTH_SHORT).show()
}
