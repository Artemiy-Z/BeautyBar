package ru.beauty.bar.ui.fragment.admin.catalog_edit.services;

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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import ru.beauty.bar.App
import ru.beauty.bar.navigation.presenter.admin.catalog_edit.services.EditServicePresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class EditService : BaseFragment() {
    override val presenter = EditServicePresenter()

    @Composable
    override fun ComposeFunction() {
        val scope = rememberCoroutineScope()

        val isEditing = App.INSTANCE.sharedData.editedService != null

        loadingBackground = MaterialTheme.colorScheme.tertiaryContainer
        loadingColor = Color.White

        val serviceName = remember { mutableStateOf("") }
        val serviceDescription = remember { mutableStateOf("") }
        val servicePictureUrl = remember { mutableStateOf("") }
        val servicePictureUri = remember { mutableStateOf<Uri?>(null) }

        if (isEditing) {
            serviceName.value = App.INSTANCE.sharedData.editedService?.name.toString()
            serviceDescription.value =
                App.INSTANCE.sharedData.editedService?.description.toString()
            servicePictureUrl.value =
                App.INSTANCE.sharedData.editedService?.previewImageLink.toString()
        }

        BaseScaffoldColumn(
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            titleText =
                if (isEditing)
                    "Редактирование услуги"
                else
                    "Создание услуги",
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
                    if (servicePictureUri.value != null) {
                        LoadAsyncImage(
                            model = servicePictureUri.value,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxHeight(),
                            contentScale = ContentScale.Fit,
                        )
                    } else if (servicePictureUrl.value.isNotEmpty()) {
                        LoadAsyncImage(
                            model = servicePictureUrl.value,
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
                                servicePictureUri.value = uri
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
                    variable = serviceName,
                    tint = Color.White
                )
                LargeInputField(
                    titleText = "Описание",
                    variable = serviceDescription,
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
                                name = serviceName.value,
                                description = serviceDescription.value,
                                previewImageLink = servicePictureUrl.value,
                                imageUri = servicePictureUri.value,
                                category = App.INSTANCE.sharedData.editedCategory!!
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
                                "Вы точно хотите удалить эту услугу?",
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
                        Text("Удалить услугу")
                    }
                }
            }
        )
    }
}