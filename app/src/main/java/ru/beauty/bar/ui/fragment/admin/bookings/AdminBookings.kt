package ru.beauty.bar.ui.fragment.admin.bookings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.beauty.bar.R
import ru.beauty.bar.dataLayer.BookingOrder
import ru.beauty.bar.navigation.presenter.admin.bookings.AdminBookingsPresenter
import ru.beauty.bar.ui.common.CardTitleDescription
import ru.beauty.bar.ui.fragment.BaseFragment
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.ui.platform.LocalLocale
import ru.beauty.bar.dataLayer.BookingOrderText
import ru.beauty.bar.ui.common.BaseScaffoldColumn

class AdminBookings : BaseFragment() {
    override val presenter = AdminBookingsPresenter()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ComposeFunction() {
        val bookingList = remember { mutableStateListOf<BookingOrderText>() }

        LaunchedEffect(Unit) {
            bookingList.clear()

            bookingList.addAll(presenter.loadBookings())
        }

        loadingColor = Color.White
        loadingBackground = MaterialTheme.colorScheme.tertiaryContainer

        BaseScaffoldColumn(
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            titleText = "Записи клиентов",
            baseFragment = this,
            onBackButtonClick = {presenter.onBackPressed()},
            content =  {
                        HorizontalDivider(thickness = 16.dp, color = Color.Transparent)

                        bookingList.forEach { item: BookingOrderText ->
                            CardTitleDescription(
                                backgroundColor = MaterialTheme.colorScheme.tertiary,
                                name = "Запись на ${item.date}",
                                description = item.category + "/" + item.service + "\n" +
                                        "Мастер: " + item.masterName + "\n" +
                                        "Клиент: " + item.userName + "\n" +
                                        "Время: " + item.time,
                                image = ImageBitmap.imageResource(R.drawable.service_collage),
                                maxLines = 6
                            )
                        }
                    }
        )
    }
}