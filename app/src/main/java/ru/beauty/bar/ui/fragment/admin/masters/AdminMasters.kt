package ru.beauty.bar.ui.fragment.admin.masters;

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.beauty.bar.dataLayer.MasterScheduleCombined
import ru.beauty.bar.database.model.Master
import ru.beauty.bar.navigation.presenter.admin.masters.AdminMastersPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class AdminMasters : BaseFragment() {
    override val presenter = AdminMastersPresenter()

    @Composable
    override fun ComposeFunction() {

        val mastersList = remember { mutableListOf<MasterScheduleCombined>() }

        loadingBackground = MaterialTheme.colorScheme.tertiaryContainer
        loadingColor = Color.White

        val trigger = remember { mutableStateOf(true) }

        LaunchedEffect(trigger.value) {
            if (trigger.value) {
                mastersList.clear()
                mastersList.addAll(presenter.loadMasters())
                trigger.value = false
            }
        }

        BaseScaffoldColumn(
            titleText = "Ваши мастера",
            onBackButtonClick = { presenter.onBackPressed() },
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            content = {
                Button(
                    onClick = { presenter.onAddPressed() }
                ) {
                    Text("Добавить мастера")
                }

                mastersList.forEach { item: MasterScheduleCombined ->
                    val experience = "Опыт работы: ${item.master?.experience.toString()} (лет)"
                    val schedule = item.scheduleString()

                    CardTitleDescription(
                        name = item.master!!.name,
                        description = experience + "\n" + schedule,
                        imageLink = item.master.pictueLink,
                        backgroundColor = MaterialTheme.colorScheme.tertiary,
                        button = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = {
                                        presenter.onEditPortfolioPressed(item)
                                    }
                                ) { Text("Портфолио") }

                                Button(
                                    onClick = {
                                        presenter.onEditPressed(item)
                                    }
                                ) { Text("Изменить") }
                            }
                        }
                    )
                }
            }
        )
    }
}