package ru.beauty.bar.ui.fragment.user.order_make.date_select;

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.beauty.bar.App
import ru.beauty.bar.navigation.presenter.user.order_make.date_select.OrderDateSelectPresenter
import ru.beauty.bar.ui.fragment.BaseFragment
import java.util.Calendar

class OrderDateSelect : BaseFragment() {
    override val presenter = OrderDateSelectPresenter()

    @OptIn(ExperimentalMaterial3Api::class)
        @Composable
    override fun ComposeFunction() {
        val buttonEnabled = remember { mutableStateOf(true) }

        val schedule = App.INSTANCE.sharedData.orderProgress!!.masterCombined!!.schedule!!

        val datePickerState = rememberDatePickerState(
            selectableDates = SelectableDatesList(
                listOf(
                    if(schedule.monday)
                        Calendar.MONDAY
                    else
                        -1,
                    if(schedule.tuesday)
                        Calendar.TUESDAY
                    else
                        -1,
                    if(schedule.wednesday)
                        Calendar.WEDNESDAY
                    else
                        -1,
                    if(schedule.thursday)
                        Calendar.THURSDAY
                    else
                        -1,
                    if(schedule.friday)
                        Calendar.FRIDAY
                    else
                        -1,
                    if(schedule.saturday)
                        Calendar.SATURDAY
                    else
                        -1,
                    if(schedule.sunday)
                        Calendar.SUNDAY
                    else
                        -1,
                )
            ), initialSelectedDateMillis = null
        )

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
                                if(datePickerState.selectedDateMillis != null) {
                                    presenter.onTimeSelectPressed(
                                        date = datePickerState.selectedDateMillis!!
                                    )
                                }
                            },
                            enabled = datePickerState.selectedDateMillis != null,
                        ) { Text(text = "К выбору времени", fontSize = 20.sp) }
                    }
                },
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Выбор даты", fontSize = 28.sp) },
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
                        })
                }) { padding ->
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

                        Text(text = "Учитывайте, что для выбора доступны только те дни, которые есть в графике выбранного вами мастера")

                        HorizontalDivider(thickness = 16.dp, color = Color.Transparent)

                        val calendar = Calendar.getInstance()

                        DatePickerDocked(
                            datePickerState = datePickerState,
                            initialPickerVisibility = true,
                            titleText = "Дата записи"
                        )
                    }
                }
            }
        }
    }
}