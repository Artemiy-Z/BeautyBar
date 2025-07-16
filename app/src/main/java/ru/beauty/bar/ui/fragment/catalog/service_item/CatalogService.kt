package ru.beauty.bar.ui.fragment.catalog.service_item;

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import ru.beauty.bar.App
import ru.beauty.bar.R
import ru.beauty.bar.navigation.presenter.catalog.sevice_item.CatalogServicePresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class CatalogService : BaseFragment() {
    override val presenter = CatalogServicePresenter()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ComposeFunction() {
        val service = App.INSTANCE.sharedData.curService
        val category = App.INSTANCE.sharedData.curCategory

        if(service == null) {
            App.INSTANCE.mainInterface.showErrorMessage(
                message = "Ошибка. Не выбрана услуга",
                dismissCallback = {presenter.onBackPressed()}
            )
            return
        }

        Surface {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.surface,
                contentWindowInsets = WindowInsets(16.dp, 16.dp, 16.dp, 16.dp),
                topBar = {
                    TopAppBar(
                        title = {
                            Column {
                                Text(text = category?.name?:"Ошибка")
                                Text(text = service.name, fontSize = 28.sp)
                            }
                        },
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
                        })
                }) { padding ->
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .verticalScroll(rememberScrollState()),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HorizontalDivider(thickness = 16.dp, color = Color.Transparent)

                        LoadAsyncImage(
                            model = service.previewImageLink,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(ShapeDefaults.Medium)
                                .background(Color.White),
                            contentScale = ContentScale.Crop,
                        )

                        HorizontalDivider(thickness = 16.dp, color = Color.Transparent)

                        Button(
                            onClick = {
                                presenter.onBookingCreatePressed()
                            }
                        ) { Text(text = "Записаться", fontSize = 20.sp) }

                        Text(
                            text = service.description,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Justify,
                        )
                    }
                }
                Box(
                    contentAlignment = Alignment.TopStart
                ) {
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
            }
        }
    }
}