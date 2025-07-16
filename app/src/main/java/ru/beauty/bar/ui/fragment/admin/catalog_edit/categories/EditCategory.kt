package ru.beauty.bar.ui.fragment.admin.catalog_edit.categories;

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import ru.beauty.bar.App
import ru.beauty.bar.navigation.presenter.admin.catalog_edit.categories.EditCategoryPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class EditCategory : BaseFragment() {
    override val presenter = EditCategoryPresenter()

    @Composable
    @Preview
    override fun ComposeFunction() {
        val scope = rememberCoroutineScope()

        val isEditing = App.INSTANCE.sharedData.editedCategory != null

        loadingBackground = MaterialTheme.colorScheme.tertiaryContainer
        loadingColor = Color.White

        val categoryName = remember { mutableStateOf("") }
        val categoryDescription = remember { mutableStateOf("") }
        val categoryPictureUrl = remember { mutableStateOf("") }
        val categoryPictureUri = remember { mutableStateOf<Uri?>(null) }

        if (isEditing) {
            categoryName.value = App.INSTANCE.sharedData.editedCategory?.name.toString()
            categoryDescription.value =
                App.INSTANCE.sharedData.editedCategory?.description.toString()
            categoryPictureUrl.value =
                App.INSTANCE.sharedData.editedCategory?.previewImageLink.toString()
        }

        BaseScaffoldColumn(
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            titleText =
                if (isEditing)
                    "Редактирование категории"
                else
                    "Создание категории",
            onBackButtonClick = { presenter.onBackPressed() },
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                        .clip(ShapeDefaults.Medium)
                        .background(MaterialTheme.colorScheme.tertiary),
                    contentAlignment = Alignment.Center
                ) {
                    if (categoryPictureUri.value != null) {
                        LoadAsyncImage(
                            model = categoryPictureUri.value,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxHeight(),
                            contentScale = ContentScale.Fit,
                        )
                    } else if (categoryPictureUrl.value.isNotEmpty()) {
                        LoadAsyncImage(
                            model = categoryPictureUrl.value,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxHeight(),
                            contentScale = ContentScale.Fit,
                        )
                    }
                }

                Button(
                    onClick = {
                        presenter.onPictureSelectPressed(
                            onSelectedListener = {uri ->
                                categoryPictureUri.value = uri
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Загрузить фото")
                }

                InputField(
                    titleText = "Название",
                    variable = categoryName,
                    tint = Color.White
                )
                LargeInputField(
                    titleText = "Описание",
                    variable = categoryDescription,
                    tint = Color.White
                )

                HorizontalDivider(
                    thickness = 32.dp,
                    color = Color.Transparent
                )

                Button(
                    onClick = {
                        scope.launch {
                            presenter.onSavePressed(
                                name = categoryName.value,
                                description = categoryDescription.value,
                                previewImageLink = categoryPictureUrl.value,
                                imageUri = categoryPictureUri.value
                            )
                        }
                    }
                ) {
                    Text("Сохранить изменения")
                }

                if (isEditing) {
                    Button(
                        onClick = {
                            App.INSTANCE.mainInterface.showChoiceMessage(
                                "Вы точно хотите удалить эту категорию?",
                                confirmCallback = {
                                    scope.launch {
                                        presenter.onDeletePressed()
                                    }
                                }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text("Удалить категорию")
                    }
                }
            }
        )
    }
}