package ru.beauty.bar.ui.fragment.catalog.categories

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import ru.beauty.bar.database.model.Category
import ru.beauty.bar.navigation.presenter.catalog.categories.CatalogCategoriesPresenter
import ru.beauty.bar.ui.common.BaseScaffoldColumn
import ru.beauty.bar.ui.common.CardTitleDescription
import ru.beauty.bar.ui.fragment.BaseFragment

class CatalogCategories : BaseFragment() {
    override val presenter = CatalogCategoriesPresenter()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ComposeFunction() {
        BaseScaffoldColumn(
            this, titleText = "Категории услуг",
            onBackButtonClick = { presenter.onBackPressed() },
            content = {
                val categoriesList = remember { mutableStateListOf<Category>() }

                val trigger = remember { mutableStateOf(true) }

                LaunchedEffect(trigger) {
                    if (trigger.value) {
                        categoriesList.clear()
                        categoriesList.addAll(presenter.loadCategories())
                        trigger.value = false
                    }
                }

                categoriesList.forEach { item: Category ->
                    CardTitleDescription(
                        contentScale = ContentScale.Crop,
                        name = item.name,
                        description = item.description,
                        button = {
                            Button(
                                onClick = {
                                    presenter.onCategorySelected(item)
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