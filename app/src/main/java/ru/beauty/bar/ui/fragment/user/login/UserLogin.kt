package ru.beauty.bar.ui.fragment.user.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.beauty.bar.navigation.presenter.user.login.UserLoginPresenter
import ru.beauty.bar.ui.common.AppLogo
import ru.beauty.bar.ui.common.InputField
import ru.beauty.bar.ui.fragment.BaseFragment

class UserLogin : BaseFragment() {
    override val presenter = UserLoginPresenter()

        @Composable
    override fun ComposeFunction() {

        val login = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }

        val scope = rememberCoroutineScope()

        Surface {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppLogo()

                    Text(text = "Вход в аккаунт", fontSize = 28.sp)

                    InputField(
                        titleText = "Логин:",
                        variable = login
                    )

                    InputField(
                        titleText = "Пароль:",
                        variable = password,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Button(
                        onClick = {
                            scope.launch {
                                presenter.login(login = login.value, password = password.value)
                            }
                        }
                    ) {
                        Text(text = "Войти")
                    }

                    Button(
                        onClick = {
                            presenter.onSignUpPressed()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Text(text = "Нет аккаунта?")
                    }

                    Button(
                        onClick = {
                            presenter.onAdminPressed()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        Text(text = "Я - администратор")
                    }

                    /*
                    Button(
                        onClick = {
                            presenter.onMasterPressed()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        Text(text = "Я - мастер")
                    }
                    */
                }
            }
        }
    }
}