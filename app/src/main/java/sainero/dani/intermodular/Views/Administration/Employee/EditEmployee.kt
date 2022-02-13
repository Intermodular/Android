package sainero.dani.intermodular.Views.Administration.Employee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.selects.select
import sainero.dani.intermodular.ViewModels.ViewModelUsers
import sainero.dani.intermodular.DataClass.Users
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.Views.Administration.ui.theme.IntermodularTheme
import java.util.regex.Pattern


@ExperimentalFoundationApi
class EditEmployee : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntermodularTheme {

            }
        }
    }
}

@Composable
fun MainEditEmployee(id: Int,viewModelUsers: ViewModelUsers) {

    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false)}
    val result = remember { mutableStateOf("") }
    var deleteUser = remember { mutableStateOf(false)}

    //Posible consulta en la Base de datos ¿? (but is ok)
    viewModelUsers.getUserById(id)

    var selectedUser: Users = Users(0,"error","","","","","","","","",false)
    viewModelUsers.userListResponse.forEach{
        if (it._id.equals(id))  selectedUser = it
    }

    //Texts
    var (textDniUser, onValueChangeDniUser) = rememberSaveable { mutableStateOf(selectedUser.dni) }
    var (dniError,dniErrorChange) = remember { mutableStateOf(false) }
    val nameOfDniError: String = "El DNI debe tener 8 numeros y un caracter"

    var (textNameUser, onValueChangeNameUser) = rememberSaveable { mutableStateOf(selectedUser.name) }
    var (nameError,nameErrorChange) = remember { mutableStateOf(false) }
    val nameOfNameError: String = "El nombre no puede contener caracteres especiales ni estar vacio"

    var (textPhoneNumberUser, onValueChangePhoneNumberUser) = rememberSaveable { mutableStateOf(selectedUser.phoneNumber) }
    var (telError,telErrorChange) = remember { mutableStateOf(false) }
    val nameOfTelError: String = "El Teléfono no es válido"

    var (textSurnameUser, onValueChangeSurnameUser) = rememberSaveable { mutableStateOf(selectedUser.surname) }
    var (surnameError,surnameErrorChange) = remember { mutableStateOf(false) }
    val nameOfsurnameError: String = "El campo Apellido no debe contener caracteres especiales"

    var (textFnacUser, onValueChangeFnacUser) = rememberSaveable { mutableStateOf(selectedUser.fnac) }
    var (fNacError,fNacErrorChange) = remember { mutableStateOf(false) }
    val nameOfFnacError: String = "La fecha de nacimiento no es válida (dd-mm-aa)"

    var (textEmailUser, onValueChangeEmailUser) = rememberSaveable { mutableStateOf(selectedUser.email) }
    var (emailError,emailErrorChange) = remember { mutableStateOf(false) }
    val nameOfEmailError: String = "El email no es válido"

    var (textUserUser, onValueChangeUserUser) = rememberSaveable { mutableStateOf(selectedUser.user) }
    var (userError,userErrorChange) = remember { mutableStateOf(false) }
    val nameOfUserError: String = "El usuario no puede estar vacio ni contener caracteres especiales"

    var (textPasswordUser, onValueChangePasswordUser) = rememberSaveable { mutableStateOf(selectedUser.password) }

    var textRolUser = rememberSaveable { mutableListOf(selectedUser.rol,"")}

    if (textRolUser[0].equals("Administrador")) textRolUser[1] = "Empleado" else textRolUser[1] = "Administrador"

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

                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "",
                            tint = Color.White
                        )
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
                LazyColumn(
                    content = {
                        item {
                            createRowListWithErrorMesaje(
                                text = "DNI",
                                value = textDniUser,
                                onValueChange = onValueChangeDniUser,
                                validateError = ::isValidDni,
                                errorMesaje = nameOfDniError,
                                changeError = dniErrorChange,
                                error = dniError,
                                mandatory = true,
                                numericTextBoard = false
                            )
                        }
                        item {
                            createRowListWithErrorMesaje(
                                text = "Name",
                                value = textNameUser,
                                onValueChange = onValueChangeNameUser,
                                validateError = ::isValidName,
                                errorMesaje = nameOfNameError,
                                changeError = nameErrorChange,
                                error = nameError,
                                mandatory = true,
                                numericTextBoard = false
                            )
                        }
                        item {
                            createRowListWithErrorMesaje(
                                text = "Surname",
                                value = textSurnameUser,
                                onValueChange = onValueChangeSurnameUser,
                                validateError = ::isValidSurname,
                                errorMesaje = nameOfsurnameError,
                                changeError = surnameErrorChange,
                                error = surnameError,
                                mandatory = true,
                                numericTextBoard = false
                            )
                        }
                        item {
                            createRowListWithErrorMesaje(
                                text = "Phone Number",
                                value = textPhoneNumberUser,
                                onValueChange = onValueChangePhoneNumberUser,
                                validateError = ::isValidPhoneNumber,
                                errorMesaje = nameOfTelError,
                                changeError = telErrorChange,
                                error = telError,
                                mandatory = false,
                                numericTextBoard = false
                            )
                        }
                        item {
                            createRowListWithErrorMesaje(
                                text = "Fecha Nacimiento",
                                value = textFnacUser,
                                onValueChange = onValueChangeFnacUser,
                                validateError = ::isValidDateOfBirth,
                                errorMesaje = nameOfFnacError,
                                changeError = fNacErrorChange,
                                error = fNacError,
                                mandatory = false,
                                numericTextBoard = false
                            )
                        }
                        item {

                            createRowListWithErrorMesaje(
                                text = "User",
                                value = textUserUser,
                                onValueChange = onValueChangeUserUser,
                                validateError = ::isValidUser,
                                errorMesaje = nameOfUserError,
                                changeError = userErrorChange,
                                error = userError,
                                mandatory = true,
                                numericTextBoard = false
                            )
                        }
                        item {
                            createRowListWithErrorMesaje(
                                text = "Email",
                                value = textEmailUser,
                                onValueChange = onValueChangeEmailUser,
                                validateError = ::isValidEmail,
                                errorMesaje = nameOfEmailError,
                                changeError = emailErrorChange,
                                error = emailError,
                                mandatory = true,
                                numericTextBoard = false
                            )
                        }
                        item {
                            dropDownMenu(text = "Rol",textRolUser)
                        }
                        item {
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
                                        textRolUser[0] = selectedUser.rol
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
                                    )
                                ) {

                                    Text(text = "Revertir cambios", fontSize = 15.sp)
                                }



                                Button(
                                    onClick = {
                                        selectedUser = Users(
                                            _id = id,
                                            dni = textDniUser,
                                            name =  textNameUser,
                                            surname =  textSurnameUser,
                                            fnac =  textFnacUser,
                                            user =  textUserUser,
                                            password =  textPasswordUser,
                                            rol =  textRolUser.get(0),
                                            email =  textEmailUser,
                                            newUser = false,
                                            phoneNumber = textPhoneNumberUser )
                                        viewModelUsers.editUser(selectedUser)

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
                                    )
                                ) {
                                    Text(text = "Guardar cambios", fontSize = 15.sp)
                                }

                                Button(
                                    onClick = {
                                        selectedUser = Users(
                                            _id = selectedUser._id,
                                            dni = selectedUser.dni,
                                            name =  selectedUser.name,
                                            surname =  selectedUser.surname,
                                            fnac =  selectedUser.fnac,
                                            user =  selectedUser.user,
                                            password =  selectedUser.password,
                                            rol =  selectedUser.rol,
                                            email =  selectedUser.email,
                                            newUser = true,
                                            phoneNumber = selectedUser.phoneNumber
                                        )
                                        viewModelUsers.editUser(selectedUser)
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
                                    )
                                ) {
                                    Text(text = "Restablecer contraseña", fontSize = 15.sp)
                                }
                            }
                        }
                    }

                )

            }
        }
    )

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



@Composable
private fun confirmDeleteUser(viewModelUsers: ViewModelUsers,id: Int) {
    MaterialTheme {

        Column {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text(text = "¿Seguro que desea eliminar al empleado seleccionado?")
                },
                text = {
                    Text("No podrás volver a recuperarlo")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModelUsers.deleteUser(id)
                            navController.navigate(Destinations.EmployeeManager.route)
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Blue,
                            contentColor = Color.White
                        ),
                    ) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            navController.navigate("${Destinations.EditEmployee.route}/${id}")
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Blue,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}


@Composable
private fun createRowListWithErrorMesaje(
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    validateError: (String) -> Boolean,
    errorMesaje: String,
    changeError: (Boolean) -> Unit,
    error: Boolean,
    mandatory: Boolean,
    numericTextBoard : Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(text = "${text}:", Modifier.width(100.dp))
        Column(
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = {
                    onValueChange(it)
                    changeError(!validateError(it))
                },
                placeholder = { Text(text) },
                label = { Text(text = text) },
                isError = error,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = if (numericTextBoard) KeyboardType.Number else KeyboardType.Text),

                modifier = Modifier
                    .padding(start = 10.dp, end = 20.dp)
            )
            val assistiveElementText = if (error) errorMesaje else if (mandatory) "*Obligatorio" else ""
            val assistiveElementColor = if (error) {
                MaterialTheme.colors.error
            } else {
                MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
            }
            Text(
                text = assistiveElementText,
                color = assistiveElementColor,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 10.dp, end = 20.dp)
            )
        }
    }
}


@Composable
private fun dropDownMenu(text: String,suggestions: List<String>) {
    Spacer(modifier = Modifier.padding(4.dp))
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(suggestions[0]) }
    var textfieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }
    var editItem = remember{ mutableStateOf(false)}

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "${text}:", Modifier.width(100.dp))
        Column() {

            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                enabled = false,
                modifier = Modifier
                    .padding(start = 10.dp, end = 20.dp)
                    .onGloballyPositioned { coordinates ->
                        textfieldSize = coordinates.size.toSize()
                    },
                trailingIcon = {
                    Icon(icon, "arrowExpanded",
                        Modifier.clickable { expanded = !expanded })
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
            ) {
                suggestions.forEach { label ->
                    DropdownMenuItem(onClick = {
                        selectedText = label
                        expanded = false
                    }) {
                        Text(text = label)
                    }
                }
            }
        }
    }
}

//Validaciones
private fun isValidDni(text: String) = Pattern.compile("^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]$", Pattern.CASE_INSENSITIVE).matcher(text).find()
private fun isValidName(text: String) = Pattern.compile("^[a-zA-Z]+$", Pattern.CASE_INSENSITIVE).matcher(text).find()
private fun isValidSurname(text: String) = Pattern.compile("^[a-zA-Z]+$", Pattern.CASE_INSENSITIVE).matcher(text).find()
private fun isValidPhoneNumber(text: String) = Pattern.compile("^([+][0-9]{2}?)?[0-9]{9}$", Pattern.CASE_INSENSITIVE).matcher(text).find()
private fun isValidDateOfBirth(text: String) = Pattern.compile("^[0-9]{1,2}[-/][0-9]{1,2}[-/][0-9]{1,4}\$", Pattern.CASE_INSENSITIVE).matcher(text).find()
private fun isValidUser(text: String) = Pattern.compile("^[a-zA-Z0-9]+\$", Pattern.CASE_INSENSITIVE).matcher(text).find()
private fun isValidEmail(text: String) = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$", Pattern.CASE_INSENSITIVE).matcher(text).find()



@Preview(showBackground = true)
@Composable
fun DefaultPreview8() {
    IntermodularTheme {
        //MainEditEmployee(1)
    }
}

