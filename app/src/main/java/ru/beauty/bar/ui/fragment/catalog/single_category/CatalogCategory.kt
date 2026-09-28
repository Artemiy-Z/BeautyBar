package ru.beauty.bar.ui.fragment.catalog.single_category

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import ru.beauty.bar.App
import ru.beauty.bar.database.model.Service
import ru.beauty.bar.navigation.presenter.catalog.single_category.CatalogCategoryPresenter
import ru.beauty.bar.ui.common.BaseScaffoldColumn
import ru.beauty.bar.ui.common.CardTitleDescription
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
            this, titleText = category.name,
            onBackButtonClick = { presenter.onBackPressed() },
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