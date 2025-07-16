package ru.beauty.bar.ui.fragment.master.login;

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.beauty.bar.navigation.presenter.master.login.MasterLoginPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class MasterLogin : BaseFragment() {
    override val presenter = MasterLoginPresenter()

    @Composable
    override fun ComposeFunction() {
        val login = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }

        val scope = rememberCoroutineScope()

        Surface(
            color = MaterialTheme.colorScheme.tertiary
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Вход в личный кабинет мастера",
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center
                    )

                    InputField(
                        titleText = "Логин:",
                        variable = login,
                        tint = Color.White
                    )

                    InputField(
                        titleText = "Пароль:",
                        variable = password,
                        tint = Color.White,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Button(
                        onClick = {
                            scope.launch {
                                presenter.login(login.value, password.value)
                            }
                        }
                    ) {
                        Text(text = "Войти")
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