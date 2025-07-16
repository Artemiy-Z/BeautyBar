package ru.beauty.bar.ui.fragment.admin.users;

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.beauty.bar.database.model.User
import ru.beauty.bar.navigation.presenter.admin.users.AdminUsersPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class AdminUsers : BaseFragment() {
    override val presenter = AdminUsersPresenter()

    @Composable
    override fun ComposeFunction() {
        val userList = remember { mutableListOf<User>() }

        loadingBackground = MaterialTheme.colorScheme.tertiaryContainer
        loadingColor = Color.White

        LaunchedEffect(Unit) {
            userList.clear()

            userList.addAll(presenter.getUserList())
        }

        BaseScaffoldColumn(
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            titleText = "Ваши клиенты",
            onBackButtonClick = {presenter.onBackPressed()},
            content = {
                userList.forEach() { item: User ->
                    CardTitleDescription(
                        name = item.name,
                        description = "Логин: ${item.login}",
                        imageLink = item.pictueLink,
                        backgroundColor = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        )
    }
}