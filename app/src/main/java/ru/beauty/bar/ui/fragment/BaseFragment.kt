package ru.beauty.bar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import ru.beauty.bar.navigation.presenter.BasePresenter
import ru.beauty.bar.ui.theme.BeautyBarTheme
import ru.beauty.bar.ui.theme.lightOnErrorContainer
import ru.beauty.bar.ui.theme.lightOnSurface
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

abstract class BaseFragment : Fragment() {

    abstract val presenter: BasePresenter
    private val showErrorDialog = mutableStateOf(false)
    private val errorMessage = mutableStateOf("")
    private var errorDismissCallback: (() -> Unit) = {}
    private val showChoiceDialog = mutableStateOf(false)
    private val choiceMessage = mutableStateOf("")
    private var choiceDismissCallback: (() -> Unit)? = null
    private var choiceConfirmCallback: (() -> Unit)? = null
    private val loading = mutableStateOf(false)
    open var loadingBackground: Color = Color.Transparent
    open var loadingColor: Color = Color.Transparent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                BeautyBarTheme {
                    ComposeFunction()
                    if (loading.value) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    if (loadingBackground != Color.Transparent)
                                        loadingBackground
                                    else
                                        MaterialTheme.colorScheme.surfaceContainer
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color =
                                    if (loadingColor != Color.Transparent)
                                        loadingColor
                                    else
                                        MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    if (showErrorDialog.value) {
                        AlertDialog(
                            onDismissRequest = {
                                showErrorDialog.value = false
                                errorDismissCallback()
                            },
                            dismissButton = {
                                Button(
                                    onClick = {
                                        showErrorDialog.value = false
                                        errorDismissCallback()
                                    }
                                ) { Text("Окей") }
                            },
                            confirmButton = {},
                            title = {
                                Text(text = "Ошибка!")
                            },
                            text = {
                                Text(text = errorMessage.value)
                            },
                            icon = {
                                Icon(Icons.Rounded.Warning, "", tint = lightOnErrorContainer)
                            }
                        )
                    }
                    if (showChoiceDialog.value) {
                        AlertDialog(
                            onDismissRequest = {
                                choiceDismissCallback?.invoke()
                                showChoiceDialog.value = false
                            },
                            dismissButton = {
                                Button(
                                    onClick = {
                                        choiceDismissCallback?.invoke()
                                        showChoiceDialog.value = false
                                    }
                                ) { Text("Нет") }
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        choiceConfirmCallback?.invoke()
                                        showChoiceDialog.value = false
                                    }
                                ) { Text(text = "Да") }
                            },
                            title = {
                                Text(text = "Внимание!")
                            },
                            text = {
                                Text(text = choiceMessage.value)
                            },
                            icon = {
                                Icon(Icons.Rounded.Info, "", tint = lightOnSurface)
                            },
                        )
                    }
                }
            }
        }
    }

    open fun toggleLoading(
        showLoading: Boolean = false
    ) {
        loading.value = showLoading
    }

    open fun onBackPressed() {
        presenter.onBackPressed()
    }

    @Composable
    open fun ComposeFunction() {
    }

    open fun showErrorMessage(message: String, dismissCallback: (() -> Unit)) {
        errorMessage.value = message
        showErrorDialog.value = true
        errorDismissCallback = dismissCallback
    }

    open fun showChoiceMessage(
        message: String,
        dismissCallback: (() -> Unit)? = null,
        confirmCallback: (() -> Unit)? = null,
    ) {
        choiceMessage.value = message
        choiceDismissCallback = dismissCallback
        choiceConfirmCallback = confirmCallback
        showChoiceDialog.value = true
    }

    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }
}