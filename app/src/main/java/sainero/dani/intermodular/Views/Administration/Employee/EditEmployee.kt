package sainero.dani.intermodular.Views.Administration.Employee

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
import sainero.dani.intermodular.ViewsItems.*
import java.util.regex.Pattern


@Composable
fun MainEditEmployee(
    _id: Int,
    mainViewModelEmployee: MainViewModelEmployee
) {

    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false)}
    val result = remember { mutableStateOf("") }
    var (deleteItem,onValueChangeDeleteItem) = remember { mutableStateOf(false)}
    var showToast = remember { mutableStateOf(false)}
    var context = LocalContext.current

    if (showToast.value) {
        Toast.makeText(context,"Debes de rellenar todos los campos correctamente",Toast.LENGTH_SHORT).show()
        showToast.value = false
    }

    //Posible consulta en la Base de datos ¿? (but is ok)
    mainViewModelEmployee.getUserById(_id)

    var selectedUser: Users = Users(0,"error","","","","","","","","","",false)
    mainViewModelEmployee.userListResponse.forEach{
        if (it._id.equals(_id))  selectedUser = it
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
    var (textAddress, onValueChangeAddress) = rememberSaveable { mutableStateOf(selectedUser.password) }

    var textRolUser = rememberSaveable { mutableStateOf("") }
    var textListRolUser = rememberSaveable { mutableListOf(selectedUser.rol,"")}

    if (textListRolUser[0].equals("Administrador")) textListRolUser[1] = "Empleado" else textListRolUser[1] = "Administrador"

    //Funciones extras a realizar
    if (deleteItem) {
        var title: String = "¿Seguro que desea eliminar el empleado seleccionado?"
        var subtitle: String = "No podrás volver a recuperarlo"

        confirmAlertDialog(
            title = title,
            subtitle = subtitle,
            onValueChangeGoBack = onValueChangeDeleteItem,
        ) {
            if (it) {
                mainViewModelEmployee.deleteUser(id = _id)
                Toast.makeText(context,"El usuario se ha eliminado correctamente",Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        }
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
                            onValueChangeDeleteItem(true)
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
                                validateError = mainViewModelEmployee::isValidDni,
                                errorMesaje = nameOfDniError,
                                changeError = dniErrorChange,
                                error = dniError,
                                mandatory = true,
                                KeyboardType = KeyboardType.Text
                            )
                        }
                        item {
                            createRowListWithErrorMesaje(
                                text = "Name",
                                value = textNameUser,
                                onValueChange = onValueChangeNameUser,
                                validateError = mainViewModelEmployee::isValidName,
                                errorMesaje = nameOfNameError,
                                changeError = nameErrorChange,
                                error = nameError,
                                mandatory = true,
                                KeyboardType = KeyboardType.Text
                            )
                        }
                        item {
                            createRowListWithErrorMesaje(
                                text = "Surname",
                                value = textSurnameUser,
                                onValueChange = onValueChangeSurnameUser,
                                validateError = mainViewModelEmployee::isValidSurname,
                                errorMesaje = nameOfsurnameError,
                                changeError = surnameErrorChange,
                                error = surnameError,
                                mandatory = true,
                                KeyboardType = KeyboardType.Text
                            )
                        }
                        item {
                            createRowListWithErrorMesaje(
                                text = "Phone Number",
                                value = textPhoneNumberUser,
                                onValueChange = onValueChangePhoneNumberUser,
                                validateError = mainViewModelEmployee::isValidPhoneNumber,
                                errorMesaje = nameOfTelError,
                                changeError = telErrorChange,
                                error = telError,
                                mandatory = false,
                                KeyboardType = KeyboardType.Phone
                            )
                        }
                        item {
                            createRowListWithErrorMesaje(
                                text = "Fecha Nacimiento",
                                value = textFnacUser,
                                onValueChange = onValueChangeFnacUser,
                                validateError = mainViewModelEmployee::isValidDateOfBirth,
                                errorMesaje = nameOfFnacError,
                                changeError = fNacErrorChange,
                                error = fNacError,
                                mandatory = false,
                                KeyboardType = KeyboardType.Text
                            )
                        }
                        item {

                            createRowListWithErrorMesaje(
                                text = "User",
                                value = textUserUser,
                                onValueChange = onValueChangeUserUser,
                                validateError = mainViewModelEmployee::isValidUser,
                                errorMesaje = nameOfUserError,
                                changeError = userErrorChange,
                                error = userError,
                                mandatory = true,
                                KeyboardType = KeyboardType.Text
                            )
                        }
                        item {
                            createRowListWithErrorMesaje(
                                text = "Email",
                                value = textEmailUser,
                                onValueChange = onValueChangeEmailUser,
                                validateError = mainViewModelEmployee::isValidEmail,
                                errorMesaje = nameOfEmailError,
                                changeError = emailErrorChange,
                                error = emailError,
                                mandatory = true,
                                KeyboardType = KeyboardType.Text
                            )
                        }
                        item {
                            textRolUser.value =
                                selectedDropDownMenu(
                                    text = "Rol",
                                    suggestions = textListRolUser
                                )
                        }
                        item { 
                            createRowList(
                                text = "Dirección",
                                value = textAddress,
                                onValueChange = onValueChangeAddress,
                                enable = true,
                                KeyboardType = KeyboardType.Text
                            )
                        }
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Button(
                                    onClick = {
                                        onValueChangeNameUser(selectedUser.name)
                                        onValueChangeSurnameUser(selectedUser.surname)
                                        onValueChangeDniUser(selectedUser.dni)
                                        onValueChangePhoneNumberUser(selectedUser.phoneNumber)
                                        onValueChangeFnacUser(selectedUser.fnac)
                                        onValueChangeUserUser(selectedUser.user)
                                        onValueChangeEmailUser(selectedUser.email)
                                        textRolUser.value = selectedUser.rol
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
                                        if (mainViewModelEmployee.checkAllValidations(
                                                textDni = textDniUser,
                                                textName = textNameUser,
                                                textSurname = textSurnameUser,
                                                textPhoneNumber = textPhoneNumberUser,
                                                textDateOfBirth = textFnacUser,
                                                textEmail = textEmailUser,
                                                textUser = textUserUser
                                                )
                                        ) {
                                            selectedUser = Users(
                                                _id = _id,
                                                dni = textDniUser,
                                                name =  textNameUser,
                                                surname =  textSurnameUser,
                                                fnac =  textFnacUser,
                                                user =  textUserUser,
                                                password =  textPasswordUser,
                                                rol =  textRolUser.value,
                                                email =  textEmailUser,
                                                newUser = false,
                                                phoneNumber = textPhoneNumberUser,
                                                address = textAddress

                                            )
                                            mainViewModelEmployee.editUser(selectedUser)
                                        }
                                        else {
                                            showToast.value = true
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
                                            phoneNumber = selectedUser.phoneNumber,
                                            address = textAddress
                                        )
                                        mainViewModelEmployee.editUser(selectedUser)
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview8() {

}

