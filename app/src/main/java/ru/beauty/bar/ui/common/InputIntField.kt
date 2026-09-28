package ru.beauty.bar.ui.common

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun InputIntField(
    titleText: String,
    variable: MutableState<Int?>,
    outlined: Boolean = true,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    val change: (String) -> Unit = {
        var it1 = it
        it1 = it1.replace(",", ".").replace(".", "")

        variable.value = it1.toIntOrNull()
        if(variable.value == 0)
            variable.value = null
    }

    if (outlined)
        OutlinedTextField(
            value = if (variable.value == null) "" else variable.value.toString(),
            onValueChange = change,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(titleText) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tint,
                focusedTextColor = tint,
                focusedLabelColor = tint,
                focusedPlaceholderColor = tint,
            ),
        )
    else {
        TextField(
            value = if (variable.value == null) "" else variable.value.toString(),
            onValueChange = change,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(titleText) },
            colors = TextFieldDefaults.colors(
                focusedTextColor = tint,
                focusedLabelColor = tint,
                focusedPlaceholderColor = tint,
            ),
        )
    }
}