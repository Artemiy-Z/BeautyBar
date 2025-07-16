package ru.beauty.bar.ui.fragment.user.account.edit

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import ru.beauty.bar.App
import ru.beauty.bar.R
import ru.beauty.bar.database.model.User
import ru.beauty.bar.navigation.presenter.BasePresenter
import ru.beauty.bar.navigation.presenter.user.account.edit.UserAccountEditPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class UserAccountEdit: BaseFragment() {
    override val presenter = UserAccountEditPresenter()

    @Composable
    override fun ComposeFunction() {
        if(App.INSTANCE.sharedData.userType != 0 || App.INSTANCE.sharedData.userData == null) {
            App.INSTANCE.mainInterface.showErrorMessage("Произошла ошибка!",
                dismissCallback = {presenter.onBackPressed()})
        }

        val user = App.INSTANCE.sharedData.userData as User

        val scope = rememberCoroutineScope()

        val imageUri = remember { mutableStateOf<Uri?>(null) }
        val name = remember { mutableStateOf(user.name) }
        val passwordOld = remember { mutableStateOf("") }
        val passwordNew = remember { mutableStateOf("") }
        val passwordNewRepeat = remember { mutableStateOf("") }

        BaseScaffoldColumn(
            titleText = "Ваш аккаунт",
            onBackButtonClick = { presenter.onBackPressed() },
            content = {
                var model: Any? = null
                if (imageUri.value != null) {
                    model = imageUri.value
                } else if (user.pictueLink.isNotEmpty()) {
                    model = user.pictueLink
                }
                LoadAsyncImage(
                    model = model,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(84.dp)
                        .aspectRatio(1f)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentScale = ContentScale.Fit,
                )

                Button(
                    onClick = {
                        presenter.onPictureSelectPressed { uri: Uri? ->
                            if(uri != null) {
                                imageUri.value = uri
                            }
                        }
                    }
                ) { Text("Загрузить фото профиля") }

                InputField(
                    variable = name,
                    titleText = "Имя"
                )

                HorizontalDivider(thickness = 8.dp, color = Color.Transparent)

                Text("Смена пароля:")

                InputField(
                    variable = passwordOld,
                    titleText = "Старый пароль"
                )

                InputField(
                    variable = passwordNew,
                    titleText = "Новый пароль"
                )

                InputField(
                    variable = passwordNewRepeat,
                    titleText = "Повтор пароля"
                )

                Button(
                    onClick = {
                        scope.launch {
                            presenter.onSavePressed(
                                id = user.id!!,
                                login = user.login,
                                name = name.value,
                                passwordOld = passwordOld.value,
                                passwordNew = passwordNew.value,
                                passwordNewRepeat = passwordNewRepeat.value,
                                imageLink = user.pictueLink,
                                imageUri = imageUri.value
                            )
                        }
                    }
                ) { Text(text = "Сохранить") }
            }
        )
    }
}