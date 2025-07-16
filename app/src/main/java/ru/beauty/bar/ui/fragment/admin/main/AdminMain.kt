package ru.beauty.bar.ui.fragment.admin.main;

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.beauty.bar.R
import ru.beauty.bar.navigation.presenter.admin.main.AdminMainPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class AdminMain : BaseFragment() {
    override val presenter = AdminMainPresenter()

    @Composable
    @Preview
    override fun ComposeFunction() {
        BaseScaffoldColumn(
            titleText = "Панель администратора",
            onBackButtonClick = {presenter.onBackPressed()},
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            content = {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                presenter.onCatalogPressed()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            shape = ShapeDefaults.Small,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary
                            )
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    bitmap = ImageBitmap
                                        .imageResource(
                                            R.drawable.service_collage
                                        ),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Text(text = "Каталог")
                            }
                        }

                        VerticalDivider(
                            thickness = 8.dp,
                            color = Color.Transparent
                        )

                        Button(
                            onClick = {
                                presenter.onBookingsPressed()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            shape = ShapeDefaults.Small,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                            )
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    bitmap = ImageBitmap
                                        .imageResource(
                                            R.drawable.checklist
                                        ),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Text(text = "Записи")
                            }
                        }
                    }

                    HorizontalDivider(
                        thickness = 8.dp,
                        color = Color.Transparent
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                presenter.onMastersPressed()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            shape = ShapeDefaults.Small,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary
                            )
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    bitmap = ImageBitmap
                                        .imageResource(
                                            R.drawable.profile_default
                                        ),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Text(text = "Мастера")
                            }
                        }

                        VerticalDivider(
                            thickness = 8.dp,
                            color = Color.Transparent
                        )

                        Button(
                            onClick = {
                                presenter.onUsersPressed()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            shape = ShapeDefaults.Small,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                            )
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    bitmap = ImageBitmap
                                        .imageResource(
                                            R.drawable.profile_default
                                        ),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Text(text = "Пользователи")
                            }
                        }
                    }
                }
            }
        )
    }
}