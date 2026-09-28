package ru.beauty.bar.ui.common

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LargeInputField(
    titleText: String,
    variable: MutableState<String>,
    outlined: Boolean = true,
    tint: Color = MaterialTheme.colorScheme.primary,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    if (outlined)
        OutlinedTextField(
            value = variable.value,
            onValueChange = { newValue -> variable.value = newValue },
            label = { Text(titleText) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tint,
                focusedTextColor = tint,
                focusedLabelColor = tint,
                focusedPlaceholderColor = tint,
            ),
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            modifier = Modifier
                .height(256.dp)
        )
    else {
        TextField(
            value = variable.value,
            onValueChange = { newValue -> variable.value = newValue },
            label = { Text(titleText) },
            colors = TextFieldDefaults.colors(
                focusedTextColor = tint,
                focusedLabelColor = tint,
                focusedPlaceholderColor = tint,
            ),
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .height(256.dp)
        )
    }
}