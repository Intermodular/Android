package sainero.dani.intermodular.Views

import android.os.Bundle
import android.view.KeyEvent.KEYCODE_ENTER
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.unit.sp
import sainero.dani.intermodular.Navigation.Destinations
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.*
import sainero.dani.intermodular.ViewModels.ViewModelUsers
import sainero.dani.intermodular.DataClass.Users
import sainero.dani.intermodular.R
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import java.util.regex.Pattern

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

    var nameError by remember { mutableStateOf(false) }



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

                    navController.navigate("${Destinations.MainAdministrationActivity.route}")
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
                    nameError = !isValidUser(it)
                },
                placeholder = { Text("User") },
                singleLine = true,
                label = { Text(text = "User") },
                isError = nameError,
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

            val assistiveElementText = if (nameError) "El usuario no puede estar vacio ni contener caracteres especiales" else ""
            val assistiveElementColor = if (nameError) {
                MaterialTheme.colors.error
            } else {
                MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
            }

            Text(
                text = assistiveElementText,
                color = assistiveElementColor,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 50.dp, end = 50.dp)
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
                        viewModelUsers.userListResponse.forEach{

                        if (it.user.equals(textUser) && it.password.equals(textPassword))
                            if (it.rol.equals("Administrador"))
                                showDialog.value = true
                            else
                                navController.navigate(Destinations.AccessToTables.route)
                    }

                    Toast.makeText(context, "El usuario o la contraseña son incorrectos",Toast.LENGTH_SHORT).show()
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


//Validaciones
private fun isValidUser(text: String) = Pattern.compile("^[a-zA-Z0-9]+\$", Pattern.CASE_INSENSITIVE).matcher(text).find()



@Composable
private fun adminAlertDestination() {
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
                            navController.navigate(Destinations.MainAdministrationActivity.route)
                        }) {
                        Text("Administración")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            navController.navigate(Destinations.AccessToTables.route)
                        }) {
                        Text("Cobrador")
                    }
                }
            )
        }
    }
}




@Preview
@Composable
fun DefaultPreview() {
   // LoginMain()

}

