package sainero.dani.intermodular.ViewsItems

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import sainero.dani.intermodular.Utils.GlobalVariables

@Composable
fun confirmAlertDialog(
    title: String,
    subtitle: String,
    onValueChangeGoBack: (Boolean) -> Unit,
    onFinishAlertDialog: (correct:Boolean) -> Unit
) {
    MaterialTheme {

        Column {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text(text = title)
                },
                text = {
                    Text(subtitle)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onFinishAlertDialog(true)
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
                            onFinishAlertDialog(false)
                            onValueChangeGoBack(false)
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

