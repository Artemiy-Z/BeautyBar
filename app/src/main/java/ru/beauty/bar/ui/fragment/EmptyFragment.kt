package ru.beauty.bar.ui.fragment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import ru.beauty.bar.navigation.presenter.EmptyPresenter

class EmptyFragment: BaseFragment() {

    override val presenter = EmptyPresenter()

    @Composable
    override fun ComposeFunction() {
        Surface {
            Box(contentAlignment = Alignment.Center) {
                Column {
                    Text(
                        text = "This is an empty screen",
                    )
                }
            }
        }
    }
}