package ru.beauty.bar.ui.fragment.user.order_make.order_confirmation;

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.beauty.bar.App
import ru.beauty.bar.navigation.presenter.user.order_make.order_confirmation.OrderConfirmPresenter
import ru.beauty.bar.ui.fragment.BaseFragment
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Locale

class OrderConfirm : BaseFragment() {
    override val presenter = OrderConfirmPresenter()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ComposeFunction() {

        if (App.INSTANCE.sharedData.orderProgress == null) {
            App.INSTANCE.mainInterface.showErrorMessage(
                message = "Ошибка. Нет данных о заказе",
                dismissCallback = { presenter.onBackPressed() }
            )
            return
        }
        val order = App.INSTANCE.sharedData.orderProgress!!

        Surface {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.surface,
                contentWindowInsets = WindowInsets(16.dp, 16.dp, 16.dp, 16.dp),
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Подтверждение записи", fontSize = 28.sp) },
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

                        OutlinedTextField(
                            readOnly = true,
                            value = order.service?.name ?: "Null",
                            label = { Text("Услуга:") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface
                            ),
                            onValueChange = {}
                        )

                        OutlinedTextField(
                            readOnly = true,
                            value = order.masterCombined?.master?.name ?: "Null",
                            label = { Text("Мастер:") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface
                            ),
                            onValueChange = {}
                        )

                        val fullMills = order.dateMills!! + order.timeMills!!
                        val fullDate = DateTimeFormatter.ISO_INSTANT
                            .format(Instant.ofEpochSecond(fullMills))
                        val sdfDate = SimpleDateFormat("dd/MM/YY", Locale.getDefault())
                        val dateString = sdfDate.format(fullMills)

                        OutlinedTextField(
                            readOnly = true,
                            value = dateString,
                            label = { Text("Дата записи:") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface
                            ),
                            onValueChange = {}
                        )

                        val sdfTime = SimpleDateFormat("HH:mm", Locale.getDefault())
                        val timeString = sdfTime.format(fullMills)

                        OutlinedTextField(
                            readOnly = true,
                            value = timeString,
                            label = { Text("Время записи:") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                                focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface
                            ),
                            onValueChange = {}
                        )

                        val scope = rememberCoroutineScope()
                        Button(
                            onClick = {
                                scope
                                    .launch {
                                        presenter.onConfirmed()
                                    }
                            }
                        ) { Text(text = "Подтвердить") }

                        Button(
                            onClick = {
                                presenter.onCancelPressed()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) { Text(text = "Отменить запись") }
                    }
                }
            }
        }
    }
}