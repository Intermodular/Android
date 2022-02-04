package sainero.dani.intermodular.Views.Administration.Employee

import android.os.Bundle
import android.os.IInterface
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import sainero.dani.intermodular.DataClass.Users
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.ViewModels.ViewModelUsers
import java.util.regex.Pattern


@ExperimentalFoundationApi
class NewEmployee : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainNewEmployee(viewModelUsers: ViewModelUsers) {

    //Texts
    var (textDniUser, onValueChangeDniUser) = rememberSaveable { mutableStateOf("") }
    var (dniError,dniErrorChange) = remember { mutableStateOf(false) }
    val nameOfDniError: String = "El DNI debe tener 8 numeros y un caracter"

    var (textNameUser, onValueChangeNameUser) = rememberSaveable { mutableStateOf("") }
    var (nameError,nameErrorChange) = remember { mutableStateOf(false) }
    val nameOfNameError: String = "El nombre no puede contener caracteres especiales ni estar vacio"

    var (textTelUser, onValueChangeTelUser) = rememberSaveable { mutableStateOf("") }
    var (telError,telErrorChange) = remember { mutableStateOf(false) }
    val nameOfTelError: String = "El Teléfono no es válido"

    var (textSurnameUser, onValueChangeSurnameUser) = rememberSaveable { mutableStateOf("") }
    var (surnameError,surnameErrorChange) = remember { mutableStateOf(false) }
    val nameOfsurnameError: String = "El campo Apellido no debe contener caracteres especiales"

    var (textFnacUser, onValueChangeFnacUser) = rememberSaveable { mutableStateOf("") }
    var (fNacError,fNacErrorChange) = remember { mutableStateOf(false) }
    val nameOfFnacError: String = "La fecha de nacimiento no es válida (dd-mm-aa)"

    var (textEmailUser, onValueChangeEmailUser) = rememberSaveable { mutableStateOf("") }
    var (emailError,emailErrorChange) = remember { mutableStateOf(false) }
    val nameOfEmailError: String = "El email no es válido"

    var (textUserUser, onValueChangeUserUser) = rememberSaveable { mutableStateOf("") }
    var (userError,userErrorChange) = remember { mutableStateOf(false) }
    val nameOfUserError: String = "El usuario no puede estar vacio ni contener caracteres especiales"

    var (textPasswordUser, onValueChangePasswordUser) = rememberSaveable { mutableStateOf("") }

    var textRolUser = rememberSaveable { mutableListOf("Administrador","Empleado")}


    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }
    val showToast = remember { mutableStateOf(false) }
    var toastMessage = rememberSaveable { mutableStateOf("")}
    val context = LocalContext.current


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

                createRowListWithErrorMesaje(
                    text = "DNI",
                    value = textDniUser,
                    onValueChange = onValueChangeDniUser,
                    validateError = ::isValidDni,
                    errorMesaje = nameOfDniError,
                    changeError = dniErrorChange,
                    error = dniError
                )

                createRowListWithErrorMesaje(
                    text = "Name",
                    value = textNameUser,
                    onValueChange = onValueChangeNameUser,
                    validateError = ::isValidName,
                    errorMesaje = nameOfNameError,
                    changeError = nameErrorChange,
                    error = nameError
                )

                createRowListWithErrorMesaje(
                    text = "Surname",
                    value = textSurnameUser,
                    onValueChange = onValueChangeSurnameUser,
                    validateError = ::isValidSurname,
                    errorMesaje = nameOfsurnameError,
                    changeError = surnameErrorChange,
                    error = surnameError
                )
                createRowListWithErrorMesaje(
                    text = "Phone Number",
                    value = textTelUser,
                    onValueChange = onValueChangeTelUser,
                    validateError = ::isValidPhoneNumber,
                    errorMesaje = nameOfTelError,
                    changeError = telErrorChange,
                    error = telError
                )

                createRowListWithErrorMesaje(
                    text = "Fecha Nacimiento",
                    value = textFnacUser,
                    onValueChange = onValueChangeFnacUser,
                    validateError = ::isValidDateOfBirth,
                    errorMesaje = nameOfFnacError,
                    changeError = fNacErrorChange,
                    error = fNacError
                )

                createRowListWithErrorMesaje(
                    text = "User",
                    value = textUserUser,
                    onValueChange = onValueChangeUserUser,
                    validateError = ::isValidUser,
                    errorMesaje = nameOfUserError,
                    changeError = userErrorChange,
                    error = userError
                )


                createRowListWithErrorMesaje(
                    text = "Email",
                    value = textEmailUser,
                    onValueChange = onValueChangeEmailUser,
                    validateError = ::isValidEmail,
                    errorMesaje = nameOfEmailError,
                    changeError = emailErrorChange,
                    error = emailError
                )

                createRowList(text = "Contraseña", value = textPasswordUser, onValueChange = onValueChangePasswordUser)
                dropDownMenu(text = "Rol",textRolUser)

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
                            if (!dniError) {
                                val newEmployee: Users = Users(
                                    _id = 0,
                                    name =  textNameUser,
                                    surname =  textSurnameUser,
                                    dni =  textDniUser,
                                    phoneNumber =  textTelUser,
                                    fnac =  textFnacUser,
                                    user =  textUserUser,
                                    password =  textPasswordUser,
                                    rol =  textRolUser.get(0),
                                    email =  textEmailUser,
                                    newUser = true
                                )
                                viewModelUsers.uploadUser(newEmployee)
                            } else {
                                showToast.value = true
                                toastMessage.value = "Debes de rellenar los campos correctamente"
                            }
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
        ToastDemo(toastMessage.value)
    }
}


@Composable
private fun createRowList(text: String, value: String, onValueChange: (String) -> Unit) {

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
                },
                placeholder = { Text(text) },
                label = { Text(text = text) },
                modifier = Modifier
                    .padding(start = 10.dp, end = 20.dp)
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
    error: Boolean
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
                isError = error,/*
                leadingIcon = {
                    if (text.equals("Phone Number"))
                    Icon(
                        imageVector = Icons.Filled.Phone,
                        contentDescription = "Decorate Phone number"
                    )
                },*/
                modifier = Modifier
                    .padding(start = 10.dp, end = 20.dp)
            )
            val assistiveElementText = if (error) errorMesaje else "*Obligatorio"
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
        var selectedText by remember { mutableStateOf(suggestions[1]) }
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
private fun isValidDni(text: String) = Pattern.compile("^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]$",Pattern.CASE_INSENSITIVE).matcher(text).find()
private fun isValidName(text: String) = Pattern.compile("^[a-zA-Z]+$",Pattern.CASE_INSENSITIVE).matcher(text).find()
private fun isValidSurname(text: String) = Pattern.compile("^[a-zA-Z]+$",Pattern.CASE_INSENSITIVE).matcher(text).find()
private fun isValidPhoneNumber(text: String) = Pattern.compile("^([+][0-9]{2}?)?[0-9]{9}$",Pattern.CASE_INSENSITIVE).matcher(text).find()
private fun isValidDateOfBirth(text: String) = Pattern.compile("^[0-9]{1,2}[-/][0-9]{1,2}[-/][0-9]{1,4}\$",Pattern.CASE_INSENSITIVE).matcher(text).find()
private fun isValidUser(text: String) = Pattern.compile("^[a-zA-Z0-9]+\$",Pattern.CASE_INSENSITIVE).matcher(text).find()
private fun isValidEmail(text: String) = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$",Pattern.CASE_INSENSITIVE).matcher(text).find()



@Preview(showBackground = true)
@Composable
fun DefaultPreview13() {
      //  MainNewEmployee()
}


@Composable
fun ToastDemo(message: String) {
    Toast.makeText(LocalContext.current,message,Toast.LENGTH_SHORT).show()
}
