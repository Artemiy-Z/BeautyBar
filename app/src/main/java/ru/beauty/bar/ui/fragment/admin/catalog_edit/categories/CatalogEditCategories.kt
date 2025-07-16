package ru.beauty.bar.ui.fragment.admin.catalog_edit.categories;

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import ru.beauty.bar.database.model.Category
import ru.beauty.bar.navigation.presenter.admin.catalog_edit.categories.CatalogEditCategoriesPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class CatalogEditCategories : BaseFragment() {
    override val presenter = CatalogEditCategoriesPresenter()

    @Composable
    override fun ComposeFunction() {
        val scope = rememberCoroutineScope()

        loadingBackground = MaterialTheme.colorScheme.tertiaryContainer
        loadingColor = Color.White

        BaseScaffoldColumn(
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            titleText = "Категории услуг",
            onBackButtonClick = { presenter.onBackPressed() },
            content = {
                val scope = rememberCoroutineScope()
                val categoriesList = remember { mutableListOf<Category>() }

                val trigger = remember { mutableStateOf(true) }

                LaunchedEffect(trigger) {
                    if (trigger.value) {
                        categoriesList.clear()
                        categoriesList.addAll(presenter.loadCategories())
                        trigger.value = false
                    }
                }

                Button(
                    onClick = {
                        presenter.onAddPressed() {
                            trigger.value = true
                        }
                    }
                ) {
                    Text("Добавить категорию")
                }

                categoriesList.forEach { item: Category ->
                    CardTitleDescription(
                        contentScale = ContentScale.Crop,
                        name = item.name,
                        description = item.description,
                        button = {
                            Button(
                                onClick = {
                                    presenter.onEditPressed(item) {
                                        trigger.value = true
                                    }
                                }
                            ) {
                                Text("Изменить")
                            }
                        },
                        imageLink = item.previewImageLink,
                        backgroundColor = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        )
    }
}