package ru.beauty.bar.ui.fragment.user.order_make.master_select;

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.beauty.bar.R
import ru.beauty.bar.dataLayer.MasterScheduleCombined
import ru.beauty.bar.navigation.presenter.user.order_make.master_select.OrderMasterSelectPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class OrderMasterSelect : BaseFragment() {
    override val presenter = OrderMasterSelectPresenter()

    @OptIn(ExperimentalMaterial3Api::class)
        @Composable
    override fun ComposeFunction() {
        val selectedOptionIndex = remember { mutableStateOf(-1) }

        val mastersList = remember { mutableListOf<MasterScheduleCombined>() }

        val trigger = remember { mutableStateOf(true) }

        LaunchedEffect(trigger.value) {
            if (trigger.value) {
                mastersList.clear()
                mastersList.addAll(presenter.loadMasters())
                trigger.value = false
            }
        }

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
                                if(selectedOptionIndex.value != -1) {
                                    presenter.onDateSelectPressed(
                                        masterCombined = mastersList[selectedOptionIndex.value]
                                    )
                                }
                            },
                            enabled = selectedOptionIndex.value != -1,
                        ) { Text(text = "К выбору даты", fontSize = 20.sp) }
                    }
                },
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Выбор мастера", fontSize = 28.sp) },
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
                        }
                    )
                }
            ) { padding ->
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .verticalScroll(rememberScrollState()),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        mastersList.forEach { item: MasterScheduleCombined ->
                            val experience = "Опыт работы: ${item.master?.experience.toString()} (лет)"
                            val schedule = item.scheduleString()

                            CardTitleDescription(
                                name = item.master!!.name,
                                description = experience + "\n" + schedule,
                                imageLink = item.master.pictueLink,
                                backgroundColor =
                                    if (selectedOptionIndex.value == mastersList.indexOf(item))
                                        MaterialTheme.colorScheme.secondaryContainer
                                    else
                                        MaterialTheme.colorScheme.surfaceContainer,
                                button = {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Button(
                                            onClick = {
                                                presenter.onMasterPortfolioPressed(
                                                    portfolio = item.master.portfloio
                                                )
                                            }
                                        ) { Text("Портфолио") }

                                        Button(
                                            onClick = {
                                                selectedOptionIndex.value = mastersList.indexOf(item)
                                            },
                                            enabled = selectedOptionIndex.value != mastersList.indexOf(item)
                                        ) { Text("Выбрать") }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}