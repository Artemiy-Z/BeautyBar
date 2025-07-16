package ru.beauty.bar.ui.fragment.master.account.view;

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.beauty.bar.navigation.presenter.master.account.view.MasterAccountViewPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class MasterAccountView : BaseFragment() {
    override val presenter = MasterAccountViewPresenter()

        @Composable
        override fun ComposeFunction() {
            Surface {
                Box (
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Column {
                        Text(text = "Ваш аккаунт")

                        Button(
                            onClick = {
                                presenter.onEditPressed()
                            }
                        ) { Text(text = "Редактировать") }

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