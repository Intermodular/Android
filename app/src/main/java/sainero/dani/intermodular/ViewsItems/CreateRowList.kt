package sainero.dani.intermodular.ViewsItems

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun createRowList(
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    enable: Boolean,
    KeyboardType: KeyboardType
) {
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
            enabled = enable,
            placeholder = { Text(text) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType =  KeyboardType),
            label = { Text(text = text) },
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
        )
    }
}