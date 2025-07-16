package ru.beauty.bar.ui.fragment.admin.catalog_edit;

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import ru.beauty.bar.R
import ru.beauty.bar.navigation.presenter.admin.catalog_edit.CatalogEditOverviewPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class CatalogEditOverview : BaseFragment() {
    override val presenter = CatalogEditOverviewPresenter()

    @Composable
    override fun ComposeFunction() {
        BaseScaffoldColumn(
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            titleText = "Редактирование каталога",
            onBackButtonClick = {
                presenter.onBackPressed()
            },
            content = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            presenter.onCategoriesPressed()
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

                            Text(text = "Категории")
                        }
                    }

                    VerticalDivider(
                        thickness = 8.dp,
                        color = Color.Transparent
                    )

                    Button(
                        onClick = {
                            presenter.onServicesPressed()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .background(MaterialTheme.colorScheme.tertiary),
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

                            Text(text = "Услуги")
                        }
                    }
                }
            }
        )
    }
}