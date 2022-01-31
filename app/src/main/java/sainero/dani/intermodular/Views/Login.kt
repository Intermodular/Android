package sainero.dani.intermodular.Views

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent.KEYCODE_ENTER
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Navigation.NavigationHost
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import sainero.dani.intermodular.Api.MainViewModel
import sainero.dani.intermodular.Controladores.ViewModelUsers
import sainero.dani.intermodular.DataClass.Users
import sainero.dani.intermodular.R
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.test

@ExperimentalFoundationApi

class Login : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


        }
    }
}



@Composable
fun LoginMain(viewModelUsers: ViewModelUsers) {

    //API
    val coroutineScope = rememberCoroutineScope()
    var allUsers: List<Users>? by remember { mutableStateOf(null) }

    //Text
    var textUser by rememberSaveable { mutableStateOf("") }
    var textPassword by rememberSaveable { mutableStateOf("") }

    //Utils
    var hidden by remember { mutableStateOf(true) }
    val showDialog = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(10.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_eros),
            contentDescription = "Logo",
            modifier = Modifier
                .height(300.dp)
                .width(500.dp)
                .padding(20.dp)
                    //Tests
                .clickable {
                    //Toast.makeText(context, test.toString(), Toast.LENGTH_SHORT).show()

                    navController.navigate(Destinations.MainAdministrationActivity.route)
                }
        )
        Column(
            verticalArrangement = Arrangement.SpaceAround,

        ) {

            Spacer(modifier = Modifier.padding(10.dp))

            OutlinedTextField(
                value = textUser,
                onValueChange = {
                    textUser = it
                },
                placeholder = { Text("Pepe") },
                singleLine = true,
                label = { Text(text = "User") },
              //  keyboardActions = KeyboardActions(),
                modifier = Modifier
                    .padding(start = 50.dp, end = 50.dp)
                    .fillMaxWidth()
                    .onKeyEvent {
                        if(it.nativeKeyEvent.keyCode.equals(KEYCODE_ENTER)) {
                            focusRequester.requestFocus()
                            true
                        }
                        false
                    }
            )


            Spacer(modifier = Modifier.padding(10.dp))
            OutlinedTextField(
                value = textPassword,
                onValueChange = {
                    textPassword = it
                },
                placeholder = { Text("password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation =
                if (hidden) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(onClick = { hidden = !hidden }) {
                        val vector = painterResource(
                            if (hidden) R.drawable.ic_visibility
                            else R.drawable.ic_visibility_off
                        )
                        val description = if (hidden) "Ocultar contraseña" else "Revelar contraseña"
                        Icon(painter = vector, contentDescription = description)
                    }
                },
                singleLine = true,
                label = { Text("Password") },
                modifier = Modifier
                    .padding(start = 50.dp, end = 50.dp)
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onKeyEvent {
                        if(it.nativeKeyEvent.keyCode.equals(KEYCODE_ENTER)) {
                            //Ejecutar botón¿?

                        }
                        false
                    }
            )

            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = {
                    GlobalScope.launch(Dispatchers.Main) {
                        val result = withContext(Dispatchers.IO){
                            viewModelUsers.getUserList()
                        }

                        Toast.makeText(context, "${viewModelUsers.userListResponse.size}",Toast.LENGTH_SHORT).show()

                    }


                    //Thread.sleep(5000)





/*
                    mainViewModel.userListResponse.forEach{

                        if (it.user.equals(textUser) && it.passwrd.equals(textPassword))
                            if (it.rol.equals("admin"))
                                showDialog.value = true
                            else
                                navController.navigate(Destinations.AccessToTables.route)
                        else
                            Toast.makeText(context, "El usuario o la contraseña son incorrectos",Toast.LENGTH_SHORT).show()
                    }*/
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Blue
                ),
                enabled = textUser.length > 0 && textPassword.length > 0,
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp)


            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Iniciar sesión",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                Text(text = "Iniciar sesión", fontSize = 20.sp)
            }
        }

        //Llamar al Dialogo
        if (showDialog.value) {
            adminAlertDestination()
        }

    }
}
/*


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    LoginMain()

}*/



@Composable
fun adminAlertDestination() {
    MaterialTheme {
        val globalVariables = GlobalVariables()

        Column {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text(text = "¿Qué función vas a desempeñar?")
                },
                text = {
                    Text("Siempre podrás volver a la anterior opción")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            GlobalVariables.navController.navigate(Destinations.MainAdministrationActivity.route)
                        }) {
                        Text("Administración")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            GlobalVariables.navController.navigate(Destinations.AccessToTables.route)
                        }) {
                        Text("Cobrador")
                    }
                }
            )
        }
    }
}





