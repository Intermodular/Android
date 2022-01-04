package sainero.dani.intermodular.Views

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import sainero.dani.intermodular.navigation.Destinations
import sainero.dani.intermodular.navigation.NavigationHost
import android.widget.Space
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import sainero.dani.intermodular.Api.MainViewModel
import sainero.dani.intermodular.DataClass.Users
import sainero.dani.intermodular.R
import sainero.dani.intermodular.navigation.NavigationHost
import sainero.dani.intermodular.ui.theme.IntermodularTheme
import androidx.navigation.NavHost as NavHost
@ExperimentalFoundationApi

class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationHost()
        }
    }
}

//Hola caracola

@Composable
fun LoginMain(navController: NavController) {
    var textUser by rememberSaveable { mutableStateOf("") }
    var textPassword by rememberSaveable { mutableStateOf("") }
    var hidden by remember { mutableStateOf(true) }
    val showDialog = remember { mutableStateOf(false) }
    //Test
    val u = Users("2","49760881J","Daniel","Sainero","25-12-2001","Pepe", "1234","prueba@gmail.com","admin")

    val vm = MainViewModel()
    LaunchedEffect(Unit, block = {vm.getUserList()})
    vm.getUserList()
    var textTest = vm.usersList.size.toString()



   /*
    MainViewModel().getUserList()
    val allUsers = MainViewModel().userListResponse
    var textTest = ""

    for(i in allUsers) {
        textTest += i.dni + ""
    }
    */

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
                .padding(20.dp),
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
                label = { Text(text = textTest) },
                modifier = Modifier
                    .padding(start = 50.dp, end = 50.dp)
                    .fillMaxWidth()
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
                label = { Text("Password") },
                modifier = Modifier
                    .padding(start = 50.dp, end = 50.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = {
                    if(u.user.equals(textUser) && u.passwrd.equals(textPassword))
                        if(u.rol.equals("admin"))
                            showDialog.value  = true
                        else
                            navController.navigate(Destinations.AccessToTables.route)
                    else {
                        //Toast.makeText(context,"El usuario o contraseña son incorrectos",Toast.LENGTH_SHORT).show()
                    }
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
            adminAlertDestination(navController)
        }

    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    LoginMain(navController)

}



@Composable
fun adminAlertDestination(navController: NavController) {
    MaterialTheme {
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





