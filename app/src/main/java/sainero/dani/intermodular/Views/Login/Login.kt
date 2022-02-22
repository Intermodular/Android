package sainero.dani.intermodular.Views

import android.view.KeyEvent.KEYCODE_ENTER
import android.view.MotionEvent
import android.widget.Toast
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.*
import sainero.dani.intermodular.DataClass.Users
import sainero.dani.intermodular.R
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.currentValidateUser
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.Views.Login.MainViewModelLogin


@ExperimentalComposeUiApi
@Composable
fun LoginMain(
    mainViewModelLogin: MainViewModelLogin
) {

    //Text
    var textUser by rememberSaveable { mutableStateOf("") }

    var textPassword by rememberSaveable { mutableStateOf("") }
    var passwordError = remember { mutableStateOf(false) }

    //Utils
    var color = remember { mutableStateOf(Color.White) }
    var colorback = remember { mutableStateOf(Color.Black) }
    var hidden by remember { mutableStateOf(true) }
    val showAlertDialog = remember { mutableStateOf(false) }
    val (showNewPassword,onValueChangeNewPassword) = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current
    var nameError by remember { mutableStateOf(false) }
    var correctUser = remember { mutableStateOf(false)}
    var selectedUser by remember { mutableStateOf(Users(0,"","","","","","","","","","",false)) }



    Column(
        modifier = Modifier
            .fillMaxSize(),
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
                    //navController.navigate("${Destinations.CreateOrder.route}/${0}")
                    //navController.navigate("${Destinations.MainAdministrationActivity.route}")
                    //navController.navigate("${Destinations.MainAdministrationActivity.route}")
                }
        )
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(text = "Iniciar Sesión", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(10.dp))

            OutlinedTextField(
                value = textUser,
                onValueChange = {
                    textUser = it
                    nameError = !mainViewModelLogin.isValidUser(it)
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                ),
                placeholder = { Text("Usuario") },
                singleLine = true,
                label = { Text(text = "Usuario") },
                isError = nameError,
                modifier = Modifier
                    .padding(start = 50.dp, end = 50.dp)
                    .fillMaxWidth()
                    .onKeyEvent {
                        if (it.nativeKeyEvent.keyCode.equals(KEYCODE_ENTER)) {
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
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                ),
                placeholder = { Text("Contraseña") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation =
                if (hidden) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(onClick = { hidden = !hidden }) {
                        val vector = painterResource(
                            if (hidden) R.drawable.ic_visibility_off
                            else R.drawable.ic_visibility
                        )
                        val description = if (hidden) "Ocultar contraseña" else "Revelar contraseña"
                        Icon(painter = vector, contentDescription = description)
                    }
                },
                singleLine = true,
                label = { Text("Contraseña") },
                modifier = Modifier
                    .padding(start = 50.dp, end = 50.dp)
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onKeyEvent {
                        if (it.nativeKeyEvent.keyCode.equals(KEYCODE_ENTER)) {
                            //Ejecutar botón¿?

                        }
                        false
                    }
            )


            Spacer(modifier = Modifier.padding(30.dp))
            OutlinedButton(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = color.value,
                ),
                enabled = textUser.length > 0 && textPassword.length > 0,
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                ),
                shape = RoundedCornerShape(20),
                border = BorderStroke(3.dp, Color.LightGray),
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                color.value = Color(0xFF003A3D)
                                colorback.value = Color.White
                            }
                            MotionEvent.ACTION_UP -> {
                                color.value = Color.White
                                colorback.value = Color.Black
                                correctUser.value = false
                                mainViewModelLogin.getUserList {
                                    mainViewModelLogin.userListResponse.forEach {

                                        if (it.user.equals(textUser) && it.password.equals(
                                                textPassword
                                            )
                                        ) {
                                            correctUser.value = true
                                            if (it.newUser) {
                                                onValueChangeNewPassword(true)
                                                selectedUser = it
                                            } else {
                                                currentValidateUser = it
                                                if (it.rol.equals("Administrador"))
                                                    showAlertDialog.value = true
                                                else
                                                    navController.navigate(Destinations.AccessToTables.route)
                                            }
                                        }
                                    }
                                    if (!correctUser.value) Toast
                                        .makeText(
                                            context,
                                            "El usuario o la contraseña son incorrectos",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            }
                        }
                        true
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Iniciar sesión",
                    modifier = Modifier.size(100.dp),
                    tint = colorback.value
                )
            }
        }


        if (showAlertDialog.value) {
            adminAlertDestination()
        }

        if (showNewPassword) {
            newPassword(
                onValueChangeNewPassword =onValueChangeNewPassword,
                mainViewModelLogin = mainViewModelLogin,
                user = selectedUser
            )
        }

    }
}



@Composable
private fun newPassword(
    onValueChangeNewPassword: (Boolean) -> Unit,
    mainViewModelLogin: MainViewModelLogin,
    user: Users
) {
    var hidden by remember { mutableStateOf(true) }
    var hidden2 by remember { mutableStateOf(true) }
    val showAlertDialog = remember { mutableStateOf(false) }
    val value = remember { mutableStateOf("")}
    val focusRequester = remember { FocusRequester() }
    val current = LocalContext.current

    var textPassword by rememberSaveable { mutableStateOf("") }
    var textPassword2 by rememberSaveable { mutableStateOf("") }
    var passwordError = remember { mutableStateOf(false) }
    var password2Error = remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = {
            onValueChangeNewPassword(false)
        },
        content = {
            Column(
                //verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
            ) {
                Row(

                ) {
                    Text(
                        text = "Escribe tu nueva contraseña",
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    )
                }

                Row(

                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    OutlinedTextField(
                        value = textPassword,
                        onValueChange = {
                            textPassword = it
                            passwordError.value = !mainViewModelLogin.isValidPassword(it)
                        },
                        placeholder = { Text(text = "Nueva contraseña") },
                        label = { Text(text = "Contraseña") },
                        isError = passwordError.value,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                        visualTransformation = if (hidden) PasswordVisualTransformation() else VisualTransformation.None,
                        trailingIcon = {
                            IconButton(onClick = { hidden = !hidden }) {
                                val vector = painterResource(
                                    if (hidden) R.drawable.ic_visibility_off
                                    else R.drawable.ic_visibility
                                )
                                val description = if (hidden) "Ocultar contraseña" else "Revelar contraseña"
                                Icon(painter = vector, contentDescription = description)
                            }
                        },
                        singleLine = true,
                        modifier = Modifier
                            .padding(start = 50.dp, end = 50.dp)
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                    )
                }
                Row(

                ) {
                    OutlinedTextField(
                        value = textPassword2,
                        onValueChange = { it2 ->
                            textPassword2 = it2
                            password2Error.value = !mainViewModelLogin.isValidPassword(it2)
                        },
                        placeholder = { Text(text = "Repetir contraseña") },
                        label = { Text(text = "Repetir Contraseña") },
                        isError = password2Error.value,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                        visualTransformation = if (hidden2) PasswordVisualTransformation() else VisualTransformation.None,
                        trailingIcon = {
                            IconButton(onClick = { hidden2 = !hidden2 }) {
                                val vector = painterResource(
                                    if (hidden2) R.drawable.ic_visibility_off
                                    else R.drawable.ic_visibility
                                )
                                val description = if (hidden2) "Ocultar contraseña" else "Revelar contraseña"
                                Icon(painter = vector, contentDescription = description)
                            }
                        },
                        singleLine = true,
                        modifier = Modifier
                            .padding(start = 50.dp, end = 50.dp)
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                    )



                }
                Row(

                ){
                    val assistiveElementText = if (password2Error.value || passwordError.value) "La contraseña no puede ser inferior a 8 caracteres ni contener caracteres especiales" else ""
                    val assistiveElementColor = if (password2Error.value || passwordError.value) {
                        MaterialTheme.colors.error
                    } else {
                        MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
                    }
                    Text(
                        text = assistiveElementText,
                        color = assistiveElementColor,
                        style = MaterialTheme.typography.caption,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                ) {

                    Button(
                        onClick = {
                            if (textPassword == textPassword2 && mainViewModelLogin.isValidPassword(textPassword)){
                                val updateUser = Users(
                                    _id = user._id,
                                    user = user.user,
                                    name = user.name,
                                    dni = user.dni,
                                    email = user.email,
                                    fnac = user.fnac,
                                    newUser = false, //true: se volvería a repetir
                                    password = textPassword,
                                    phoneNumber = user.phoneNumber,
                                    rol = user.rol,
                                    surname = user.surname,
                                    address = user.address
                                )

                                mainViewModelLogin.editUser(user = updateUser) {
                                    mainViewModelLogin.getUserList{}
                                    Toast.makeText(current,"La contraseña ha sido modificada",Toast.LENGTH_SHORT).show()
                                }
                                onValueChangeNewPassword(false)

                            }else{

                                Toast.makeText(current,"Revisa las validaciones y que las contraseñas sean iguales",Toast.LENGTH_SHORT).show()
                            }


                        },
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
                        Text(text = "Modificar Contraseña", fontSize = 18.sp, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    )
}



@Composable
private fun adminAlertDestination() {
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




@Preview
@Composable
fun DefaultPreview() {
   // LoginMain()

}

