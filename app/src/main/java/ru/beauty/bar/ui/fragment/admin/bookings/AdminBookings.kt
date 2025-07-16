package ru.beauty.bar.ui.fragment.admin.bookings;

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.beauty.bar.App
import ru.beauty.bar.R
import ru.beauty.bar.dataLayer.BookingOrder
import ru.beauty.bar.database.model.User
import ru.beauty.bar.navigation.presenter.admin.bookings.AdminBookingsPresenter
import ru.beauty.bar.ui.fragment.BaseFragment
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Locale

class AdminBookings : BaseFragment() {
    override val presenter = AdminBookingsPresenter()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ComposeFunction() {
        val bookingList = remember { mutableListOf<BookingOrder>() }

        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            bookingList.clear()

            bookingList.addAll(presenter.loadBookings())
        }

        loadingColor = Color.White
        loadingBackground = MaterialTheme.colorScheme.tertiaryContainer

        Surface {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentWindowInsets = WindowInsets(16.dp, 16.dp, 16.dp, 16.dp),
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Записи пользователей", fontSize = 28.sp) },
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
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HorizontalDivider(thickness = 16.dp, color = Color.Transparent)

                        bookingList.forEach { item: BookingOrder ->
                            val fullMills = item.fullMills!!
                            val fullDate = DateTimeFormatter.ISO_INSTANT
                                .format(Instant.ofEpochSecond(fullMills))
                            val sdfDate = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
                            val dateString = sdfDate.format(fullMills)
                            val sdfTime = SimpleDateFormat("HH:mm", Locale.getDefault())
                            val timeString = sdfTime.format(fullMills)

                            CardTitleDescription(
                                backgroundColor = MaterialTheme.colorScheme.tertiary,
                                name = "Запись на $dateString",
                                description = item.category?.name+"/"+item.service?.name+"\n" +
                                        "Мастер: "+item.masterCombined?.master?.name+"\n" +
                                        "Клиент: "+item.user?.login+"\n" +
                                        "Время: "+timeString,
                                image = ImageBitmap.imageResource(R.drawable.service_collage),
                            )
                        }
                    }
                }
            }
        }
    }
}