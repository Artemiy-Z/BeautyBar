package ru.beauty.bar.ui.fragment.master.bookings;

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.beauty.bar.navigation.presenter.master.bookings.MasterBookingsPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class MasterBookings : BaseFragment() {
    override val presenter = MasterBookingsPresenter()

        @Composable
        override fun ComposeFunction() {
            Surface {
                Box (
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Column {
                        Text(text = "Ваши записи")

                        Button(
                            onClick = {
                                presenter.onBackPressed()
                            }
                        ) { Text(text = "Назад") }
                    }
                }
            }
        }
}