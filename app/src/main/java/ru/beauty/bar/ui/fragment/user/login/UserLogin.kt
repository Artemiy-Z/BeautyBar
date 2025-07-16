package ru.beauty.bar.ui.fragment.user.login;

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.beauty.bar.R
import ru.beauty.bar.navigation.presenter.user.login.UserLoginPresenter
import ru.beauty.bar.ui.fragment.BaseFragment
import kotlin.coroutines.coroutineContext

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