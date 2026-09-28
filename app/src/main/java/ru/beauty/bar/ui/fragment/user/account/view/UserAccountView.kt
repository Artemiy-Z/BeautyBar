package ru.beauty.bar.ui.fragment.user.account.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import ru.beauty.bar.App
import ru.beauty.bar.database.model.User
import ru.beauty.bar.navigation.presenter.user.account.view.UserAccountViewPresenter
import ru.beauty.bar.ui.common.BaseScaffoldColumn
import ru.beauty.bar.ui.common.LoadAsyncImage
import ru.beauty.bar.ui.fragment.BaseFragment

class UserAccountView : BaseFragment() {
    override val presenter = UserAccountViewPresenter()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ComposeFunction() {

        if(App.INSTANCE.sharedData.userType != 0 || App.INSTANCE.sharedData.userData == null) {
            App.INSTANCE.mainInterface.showErrorMessage("Произошла ошибка!",
                dismissCallback = {presenter.onBackPressed()})
        }

        val user = App.INSTANCE.sharedData.userData as User

        BaseScaffoldColumn(
            this, titleText = "Ваш аккаунт",
            onBackButtonClick = { presenter.onBackPressed() },
            content = {
                LoadAsyncImage(
                    model = user.pictureLink,
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

                OutlinedTextField(
                    readOnly = true,
                    value = user.name,
                    label = { Text("Имя:") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    onValueChange = {}
                )

                Button(
                    onClick = {
                        presenter.onEditPressed()
                    }
                ) { Text(text = "Редактировать") }
            }
        )
    }
}
