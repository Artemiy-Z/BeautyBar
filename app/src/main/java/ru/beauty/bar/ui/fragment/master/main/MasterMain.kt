package ru.beauty.bar.ui.fragment.master.main;

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.beauty.bar.navigation.presenter.master.main.MasterMainPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class MasterMain : BaseFragment() {
    override val presenter = MasterMainPresenter()

        @Composable
        override fun ComposeFunction() {
            Surface {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Column {
                        Text(text = "Главная")

                        val showAlertDialog = remember { mutableStateOf(false) }

                        if(showAlertDialog.value) {
                            AlertDialog(
                                onDismissRequest = {
                                    showAlertDialog.value = false
                                },
                                dismissButton = {
                                    Button(
                                        onClick = {
                                            showAlertDialog.value = false
                                        }
                                    ) { Text("Нет") }
                                },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            showAlertDialog.value = false
                                            presenter.onBackPressed()
                                        }
                                    ) { Text("Да") }
                                },
                                title = { Text(text = "Вы точно хотите выйти?") }
                            )
                        }

                        Button(
                            onClick = {
                                presenter.onAccountPressed()
                            }
                        ) {
                            Text(text = "Аккаунт")
                        }

                        Button(
                            onClick = {
                                presenter.onBookingsPressed()
                            }
                        ) {
                            Text(text = "Записи")
                        }

                        Button(
                            onClick = {
                                presenter.onCatalogPressed()
                            }
                        ) {
                            Text(text = "Каталог")
                        }

                        Button(
                            onClick = {
                                showAlertDialog.value = true
                            }
                        ) {
                            Text(text = "Выйти")
                        }
                    }
                }
            }
        }
}