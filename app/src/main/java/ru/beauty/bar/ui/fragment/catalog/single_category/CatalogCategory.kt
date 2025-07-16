package ru.beauty.bar.ui.fragment.catalog.single_category;

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.beauty.bar.App
import ru.beauty.bar.R
import ru.beauty.bar.database.model.Category
import ru.beauty.bar.database.model.Service
import ru.beauty.bar.navigation.presenter.catalog.single_category.CatalogCategoryPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class CatalogCategory : BaseFragment() {
    override val presenter = CatalogCategoryPresenter()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ComposeFunction() {

        if(App.INSTANCE.sharedData.curCategory == null) {
            App.INSTANCE.mainInterface.showErrorMessage(
                message = "Ошибка: не выбрана категория!",
                dismissCallback = {presenter.onBackPressed()}
            )
        }

        val category = App.INSTANCE.sharedData.curCategory!!

        val servicesList = remember { mutableListOf<Service>() }

        val trigger = remember { mutableStateOf(true) }

        LaunchedEffect(trigger) {
            if(trigger.value) {
                servicesList.clear()
                servicesList.addAll(presenter.loadServices(category = category))
                trigger.value = false
            }
        }

        BaseScaffoldColumn(
            titleText = category.name,
            onBackButtonClick = {presenter.onBackPressed()},
            content = {
                servicesList.forEach { item: Service ->
                    CardTitleDescription(
                        contentScale = ContentScale.Crop,
                        name = item.name,
                        description = item.description,
                        button = {
                            Button(
                                onClick = {
                                    presenter.onServiceSelected(item)
                                }
                            ) {
                                Text("Перейти")
                            }
                        },
                        imageLink = item.previewImageLink,
                    )
                }
            }
        )
    }
}