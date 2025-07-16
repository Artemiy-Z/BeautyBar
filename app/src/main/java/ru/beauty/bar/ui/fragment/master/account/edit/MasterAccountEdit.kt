package ru.beauty.bar.ui.fragment.master.account.edit;

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.beauty.bar.navigation.presenter.master.account.edit.MasterAccountEditPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class MasterAccountEdit : BaseFragment() {
    override val presenter = MasterAccountEditPresenter()

        @Composable
        override fun ComposeFunction() {
            Surface {
                Box (
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Column {
                        Text(text = "Редактирование аккаунта")

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