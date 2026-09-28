package ru.beauty.bar.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import ru.beauty.bar.ui.fragment.BaseFragment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
    baseFragment: BaseFragment, datePickerState: DatePickerState,
    initialPickerVisibility: Boolean = false,
    titleText: String = ""
) {
    var showDatePicker by remember { mutableStateOf(initialPickerVisibility) }
    val selectedDate = datePickerState.selectedDateMillis?.let {
        baseFragment.convertMillisToDate(it)
    } ?: ""

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("Дата записи") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Выберите дату"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Box(
                modifier = Modifier
                    .padding(0.dp, 64.dp, 0.dp, 0.dp)
            ) {
                Popup(
                    onDismissRequest = { showDatePicker = false },
                    alignment = Alignment.TopStart,
                    properties = PopupProperties(
                        clippingEnabled = true
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(elevation = 4.dp)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        DatePicker(
                            state = datePickerState,
                            showModeToggle = false,
                            title = {
                                val modifier = Modifier.padding(
                                    PaddingValues(
                                        start = 24.dp,
                                        end = 12.dp,
                                        top = 16.dp
                                    )
                                )
                                when (datePickerState.displayMode) {
                                    DisplayMode.Input ->
                                        Text(text = "Введите дату:", modifier = modifier)

                                    DisplayMode.Picker ->
                                        Text(text = "Выберите дату:", modifier = modifier)
                                }
                            },
                            headline = {
                                Text(
                                    titleText,
                                    Modifier.padding(
                                        PaddingValues(
                                            start = 24.dp,
                                            end = 12.dp,
                                            bottom = 12.dp
                                        )
                                    )
                                )
                            },
                            colors = DatePickerDefaults.colors(
                                dayContentColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }
            }
        }
    }
}