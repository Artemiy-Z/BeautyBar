package ru.beauty.bar.ui.fragment.admin.catalog_edit.services;

import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import ru.beauty.bar.App
import ru.beauty.bar.database.model.Category
import ru.beauty.bar.database.model.Service
import ru.beauty.bar.navigation.presenter.admin.catalog_edit.services.CatalogEditServicesPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class CatalogEditServices : BaseFragment() {
    override val presenter = CatalogEditServicesPresenter()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ComposeFunction() {
        val servicesList = remember { mutableListOf<Service>() }

        val trigger = remember { mutableStateOf(true) }

        val categoryList = remember { mutableListOf<Category?>() }
        val selectedCategory = remember { mutableStateOf<Category?>(null) }

        loadingBackground = MaterialTheme.colorScheme.tertiaryContainer
        loadingColor = MaterialTheme.colorScheme.onTertiary

        LaunchedEffect(trigger.value) {
            if (trigger.value) {
                if (categoryList.isEmpty()) {
                    categoryList.clear()
                    categoryList.add(null)
                    categoryList.addAll(presenter.loadCategories())
                }

                servicesList.clear()
                servicesList.addAll(
                    presenter.loadServices(
                        category = selectedCategory.value
                    )
                )

                trigger.value = false
            }
        }

        BaseScaffoldColumn(
            titleText = "Редактирование услуг",
            onBackButtonClick = { presenter.onBackPressed() },
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            content = {
                val expanded = remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expanded.value,
                    onExpandedChange = {
                        expanded.value = !expanded.value
                    },
                ) {
                    OutlinedTextField(
                        label = { Text("Категория") },
                        readOnly = true,
                        value =
                            if (selectedCategory.value != null)
                                selectedCategory.value!!.name
                            else
                                "(Выбрать)",
                        onValueChange = {},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded.value
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                            focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                            focusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                            focusedTrailingIconColor = MaterialTheme.colorScheme.onTertiary,
                            focusedBorderColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onTertiary,
                        ),
                        modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                    )
                    ExposedDropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = {
                            expanded.value = false
                        },
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ) {
                        categoryList.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text =
                                            selectionOption?.name ?: "(Выбрать)"
                                    )
                                },
                                onClick = {
                                    selectedCategory.value = selectionOption
                                    expanded.value = false
                                    trigger.value = true
                                },
                                colors = MenuDefaults.itemColors(
                                    textColor = MaterialTheme.colorScheme.onTertiary,
                                )
                            )
                        }
                    }
                }

                if(selectedCategory.value != null) {
                    Button(
                        onClick = {
                            presenter.onAddPressed(
                                category = selectedCategory.value
                            ) {
                                trigger.value = true
                            }
                        }
                    ) {
                        Text("Добавить услугу")
                    }
                }

                servicesList.forEach { item: Service ->
                    CardTitleDescription(
                        contentScale = ContentScale.Crop,
                        name = item.name,
                        description = item.description,
                        button = {
                            Button(
                                onClick = {
                                    presenter.onEditPressed(
                                        category = selectedCategory.value,
                                        service = item
                                    ) {
                                        trigger.value = true
                                    }
                                }
                            ) {
                                Text("Изменить")
                            }
                        },
                        imageLink = item.previewImageLink,
                        maxLines = 3,
                        backgroundColor = MaterialTheme.colorScheme.tertiary,
                    )
                }
            }
        )
    }
}