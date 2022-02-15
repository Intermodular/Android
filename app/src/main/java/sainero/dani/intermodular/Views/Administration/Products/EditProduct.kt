package sainero.dani.intermodular.Views.Administration.Products

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius.Companion.Zero
import androidx.compose.ui.geometry.Offset.Companion.Zero
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.bumptech.glide.util.Util
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Navigation.NavigationHost
import sainero.dani.intermodular.R
import sainero.dani.intermodular.Utils.GlobalVariables
import sainero.dani.intermodular.Utils.GlobalVariables.Companion.navController
import sainero.dani.intermodular.ViewModels.ViewModelProductos
import sainero.dani.intermodular.ViewModels.ViewModelTipos
import sainero.dani.intermodular.Views.Administration.Employee.ToastDemo
import java.io.File
import java.util.function.ToDoubleBiFunction
import java.util.regex.Pattern


@ExperimentalFoundationApi
class EditProduct : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MainEditProduct(id: Int,viewModelProductos: ViewModelProductos,viewModelTipos : ViewModelTipos) {
    var scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val expanded = remember { mutableStateOf(false) }

    viewModelProductos.getProductById(id)
    var ingredientes: MutableList<String> = mutableListOf()
    var especification: MutableList<String> = mutableListOf()

    //Consultas BD
    var selectedProduct: Productos = Productos(0,"","",ingredientes,3.4f, especification,"",1)
    viewModelProductos.productListResponse.forEach{
        if (it._id.equals(id))  selectedProduct = it
    }

    var allTypes = viewModelTipos.typeListResponse
    var allTypesNames: MutableList<String> = mutableListOf()
    allTypes.forEach{allTypesNames.add(it.name)}

    //Textos
    var (textName, onValueChangeName) = rememberSaveable{ mutableStateOf(selectedProduct.name) }
    var (nameError,nameErrorChange) = remember { mutableStateOf(false) }
    val nameOfNameError: String = "El nombre no puede contener caracteres especiales ni ser mayor de 20"


    var textType = rememberSaveable{mutableStateOf(selectedProduct.type)}
    ingredientes = selectedProduct.ingredients


    var (textCost, onValueChangeCost) = rememberSaveable{mutableStateOf(selectedProduct.price.toString())}
    var (costError,costErrorChange) = remember { mutableStateOf(false) }
    val costOfNameError: String = "El número debe de estar sin ','"



    especification = selectedProduct.especifications
    var (textImg, onValueChangeImg) = rememberSaveable{mutableStateOf(selectedProduct.img)}

    var (textStock, onValueChangeStock) = rememberSaveable{mutableStateOf(selectedProduct.stock.toString())}
    var (stockError,stockErrorChange) = remember { mutableStateOf(false) }
    val stockOfNameError: String = "Debe ser un número entero"



    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Edición de productos",color = Color.White)
                },
                backgroundColor = Color.Blue,
                elevation = AppBarDefaults.TopAppBarElevation,
                actions = {
                    IconButton(onClick = {
                        viewModelProductos.deleteProduct(id)
                        navController.navigate(Destinations.ProductManager.route)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete Icon",
                            tint = Color.White
                        )
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
            LazyColumn(
                Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth(),
                content = {
                    item {
                        Spacer(modifier = Modifier.padding(10.dp))
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally

                        ) {
                            Card(
                                modifier = Modifier
                                    .padding(8.dp, 4.dp)
                                    .fillMaxWidth(0.5f)
                                    .height(150.dp),
                                shape = RoundedCornerShape(200.dp),
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = selectedProduct.img,
                                        builder = {
                                            scale(Scale.FIT)
                                            transformations(CircleCropTransformation())
                                            crossfade(true)
                                        },
                                    ),
                                    contentDescription = "Imagen del producto ${selectedProduct.name}",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.padding(10.dp))

                    }
                    item {
                        createRowListWithErrorMesaje(
                            text = "Nombre",
                            value = textName,
                            onValueChange = onValueChangeName,
                            validateError = ::isValidNameOfProduct,
                            errorMesaje = nameOfNameError,
                            changeError = nameErrorChange,
                            error = nameError,
                            mandatory = true,
                            numericTextBoard = false
                        )
                    }
                    item {
                        textType.value = selectedDropDownMenu("Tipo",allTypesNames)
                    }
                    item {
                        dropDownMenu("Ingredientes",ingredientes,id,"${Destinations.Ingredient.route}/${id}")
                    }
                    item {
                        createRowListWithErrorMesaje(
                            text = "Coste",
                            value = textCost,
                            onValueChange = onValueChangeCost,
                            validateError = ::isValidCostOfProduct,
                            errorMesaje = costOfNameError,
                            changeError = costErrorChange,
                            error = costError,
                            mandatory = true,
                            numericTextBoard = true
                        )
                    }

                    item {
                        dropDownMenu("Especificaciones",especification,id,"${Destinations.Especifications.route}/${id}")
                    }

                    item {
                        createRowList("Imágen",textImg,onValueChangeImg)
                    }

                    item {
                        createRowListWithErrorMesaje(
                            text = "Stock",
                            value = textStock,
                            onValueChange = onValueChangeStock,
                            validateError = ::isValidStockOfProduct,
                            errorMesaje = stockOfNameError,
                            changeError = stockErrorChange,
                            error = stockError,
                            mandatory = false,
                            numericTextBoard = true
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.padding(10.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Button(
                                onClick = {
                                    textName = ""
                                    textType.value = selectedProduct.type
                                    textCost = ""
                                    textImg = ""
                                    textStock = ""
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
                                    //Guardar los cambios en la BD
                                    val product: Productos = Productos(id,textName,textType.value,ingredientes,textCost.toFloat(),especifications = especification,textImg,textStock.toInt())
                                    viewModelProductos.editProduct(product = product)
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
                        Spacer(modifier = Modifier.padding(20.dp))

                    }
                }
            )
        }
    )

}

@Composable
private fun selectedDropDownMenu(text: String,suggestions: List<String>): String {
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
    return selectedText
}

@Composable
private fun dropDownMenu(text: String,suggestions: List<String>, idOfItem: Int,navigate: String) {
    Spacer(modifier = Modifier.padding(4.dp))
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(text) }
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
                },
                leadingIcon = {
                    Icon(Icons.Default.Edit,"Edit ${text}",
                        Modifier.clickable{
                            editItem.value = true
                            navController.navigate(navigate)

                        })
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


//Validaciones
private fun isValidNameOfProduct(text: String) = Pattern.compile("^[a-zA-Z ]{1,20}$", Pattern.CASE_INSENSITIVE).matcher(text).find()
private fun isValidCostOfProduct(text: String) = Pattern.compile("^[0-9.]{1,20}$", Pattern.CASE_INSENSITIVE).matcher(text).find()
private fun isValidStockOfProduct(text: String) = Pattern.compile("^[0-9]{1,20}$", Pattern.CASE_INSENSITIVE).matcher(text).find()



@Preview(showBackground = true)
@Composable
fun DefaultPreview11() {
       // MainEditProduct("")
}


///////////////////

/*
private fun getDirectory(): File {
    val mediaDir = externalMediaDirs.firstOrNull()?.let {
        File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else filesDir
}*/




@Composable
fun CamerOpen(directory: File) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    SimpleCameraPreview(
        modifier = Modifier.fillMaxSize(),
        context = context,
        lifecycleOwner = lifecycleOwner,
        outputDirectory = directory,
        onMediaCaptured = { url -> }
    )
}



@Composable
fun SimpleCameraPreview(
    modifier: Modifier = Modifier,
    context: Context,
    lifecycleOwner: LifecycleOwner,
    outputDirectory: File,
    onMediaCaptured: (Uri?) -> Unit
) {
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    var preview by remember { mutableStateOf<Preview?>(null) }
    val camera: Camera? = null
    var lensFacing by remember { mutableStateOf(CameraSelector.LENS_FACING_BACK) }
    var flashEnabled by remember { mutableStateOf(false) }
    var flashRes by remember { mutableStateOf(R.drawable.administrador) }
    val executor = ContextCompat.getMainExecutor(context)
    var cameraSelector: CameraSelector?
    val cameraProvider = cameraProviderFuture.get()
/*
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            cameraProviderFuture.addListener({
                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .apply {
                      //  setAnalyzer(executor, FaceAnalyzer())
                    }
                imageCapture = ImageCapture.Builder()
                    .setTargetRotation(previewView.display.rotation)
                    .build()

                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    imageCapture,
                    preview
                )
            }, executor)
            preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            previewView
        }
    )
*/
    IconButton(
        onClick = {
            camera?.let {
                if (it.cameraInfo.hasFlashUnit()) {
                    flashEnabled = !flashEnabled
                    flashRes = if (flashEnabled) R.drawable.administrador else
                        R.drawable.empleado
                    it.cameraControl.enableTorch(flashEnabled)
                }
            }
        }
    ) {
        Icon(
            painter = painterResource(id = flashRes),
            contentDescription = "",
            modifier = Modifier.size(35.dp),
            tint = MaterialTheme.colors.surface
        )
    }
}

