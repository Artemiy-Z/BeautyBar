package ru.beauty.bar.ui.fragment.user.order_make.time_select;

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.beauty.bar.App
import ru.beauty.bar.database.model.Booking
import ru.beauty.bar.navigation.presenter.user.order_make.time_select.OrderTimeSelectPresenter
import ru.beauty.bar.ui.fragment.BaseFragment
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class OrderTimeSelect : BaseFragment() {
    override val presenter = OrderTimeSelectPresenter()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ComposeFunction() {
        val buttonEnabled = remember { mutableStateOf(true) }

        val otherBookings = remember { mutableListOf<Booking>() }
        val otherTimeSelections = remember { mutableListOf<String>() }

        LaunchedEffect(Unit) {
            try {
                otherBookings.addAll(presenter.loadBookingsByDate(
                    dateMills = App.INSTANCE.sharedData.orderProgress!!.dateMills,
                    master = App.INSTANCE.sharedData.orderProgress!!.masterCombined!!.master!!
                ))

                for(item in otherBookings) {
                    val sdf = SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss", Locale.getDefault())

                    val fullMills = sdf.parse(item.bookingDateTime)?.time

                    val sdfTime = SimpleDateFormat("HH:mm", Locale.getDefault())
                    val timeString = sdfTime.format(fullMills)

                    if(!otherTimeSelections.contains(timeString)) {
                        otherTimeSelections.add(timeString)
                    }
                    else {
                        App.INSTANCE.mainInterface.showErrorMessage("Две записи на одно время, одного мастера и дату")
                    }
                }
            }
            catch (e:Exception) {
                App.INSTANCE.mainInterface.showErrorMessage(e.localizedMessage)
            }
        }

        val timeOptions = listOf("(выбрать)", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00")
        val selectedTime = remember { mutableStateOf(timeOptions[0]) }

        Surface {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.surface,
                contentWindowInsets = WindowInsets(16.dp, 16.dp, 16.dp, 16.dp),
                floatingActionButton = {
                    Box(
                        Modifier.padding(0.dp, 0.dp, 0.dp, 32.dp)
                    )
                    {
                        Button(
                            onClick = {
                                if (selectedTime.value != "(выбрать)") {
                                    val dtf = DateTimeFormatter.ofPattern("HH:mm")
                                    val lt = LocalTime.parse(selectedTime.value, dtf)
                                    val ldt = lt.atDate(LocalDate.ofEpochDay(0L))
                                    val mills = ldt.atZone(ZoneId.systemDefault())
                                        .toInstant()
                                        .toEpochMilli()

                                    presenter.onConfirmPressed(
                                        time = mills
                                    )
                                }
                            },
                            enabled = selectedTime.value != "(выбрать)",
                        ) { Text(text = "К подтверждению", fontSize = 20.sp) }
                    }
                },
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Выбор времени", fontSize = 28.sp) },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    presenter.onBackPressed()
                                },

                                ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                    contentDescription = ""
                                )
                            }
                        }
                    )
                }
            ) { padding ->
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .verticalScroll(rememberScrollState()),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HorizontalDivider(thickness = 16.dp, color = Color.Transparent)

                        val expanded = remember { mutableStateOf(true) }

                        ExposedDropdownMenuBox(
                            expanded = expanded.value,
                            onExpandedChange = {
                                expanded.value = !expanded.value
                            },
                        ) {
                            OutlinedTextField(
                                label = { Text("Выберите время:") },
                                readOnly = true,
                                value = selectedTime.value,
                                onValueChange = {},
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = expanded.value
                                    )
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                            )
                            ExposedDropdownMenu(
                                expanded = expanded.value,
                                onDismissRequest = {
                                    expanded.value = false
                                }
                            ) {
                                timeOptions.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        text = { Text(
                                            text = selectionOption +
                                                    if(otherTimeSelections.contains(selectionOption))" (занято)" else ""
                                        ) },
                                        onClick = {
                                            if(otherTimeSelections.contains(selectionOption))
                                                return@DropdownMenuItem

                                            selectedTime.value = selectionOption
                                            expanded.value = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}