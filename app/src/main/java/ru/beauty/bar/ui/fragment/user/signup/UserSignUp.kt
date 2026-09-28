package ru.beauty.bar.ui.fragment.user.signup

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
import ru.beauty.bar.navigation.presenter.user.signup.UserSignUpPresenter
import ru.beauty.bar.ui.common.AppLogo
import ru.beauty.bar.ui.common.InputField
import ru.beauty.bar.ui.fragment.BaseFragment

class UserSignUp : BaseFragment() {
    override val presenter = UserSignUpPresenter()

        @Composable
    override fun ComposeFunction() {

        val login = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val passwordRepeat = remember { mutableStateOf("") }
        val name = remember { mutableStateOf("") }

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

                    Text(text = "Регистрация", fontSize = 28.sp)

                    InputField(
                        titleText = "Имя:",
                        variable = name
                    )

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

                    InputField(
                        titleText = "Повтор пароля:",
                        variable = passwordRepeat,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Button(
                        onClick = {
                            scope.launch {
                                presenter.signUp(
                                    name = name.value,
                                    login = login.value,
                                    password = password.value,
                                    passwordRepeat = passwordRepeat.value
                                )
                            }
                        }
                    ) {
                        Text(text = "Зарегистрироваться")
                    }

                    Button(
                        onClick = {
                            presenter.onBackPressed()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(text = "Назад")
                    }
                }
            }
        }
    }
}