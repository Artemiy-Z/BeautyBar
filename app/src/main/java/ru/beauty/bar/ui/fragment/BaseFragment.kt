package ru.beauty.bar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.fragment.app.Fragment
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import ru.beauty.bar.R
import ru.beauty.bar.navigation.presenter.BasePresenter
import ru.beauty.bar.ui.theme.BeautyBarTheme
import ru.beauty.bar.ui.theme.lightOnErrorContainer
import ru.beauty.bar.ui.theme.lightOnSurface
import java.text.SimpleDateFormat
import java.util.Calendar
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

    @Composable
    fun InputField(
        titleText: String,
        variable: MutableState<String>,
        outlined: Boolean = true,
        tint: Color = MaterialTheme.colorScheme.primary,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        visualTransformation: VisualTransformation = VisualTransformation.None
    ) {
        if (outlined)
            OutlinedTextField(
                value = variable.value,
                onValueChange = { newValue -> variable.value = newValue },
                label = { Text(titleText) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = tint,
                    focusedTextColor = tint,
                    focusedLabelColor = tint,
                    focusedPlaceholderColor = tint,
                ),
                keyboardOptions = keyboardOptions,
                visualTransformation = visualTransformation
            )
        else {
            TextField(
                value = variable.value,
                onValueChange = { newValue -> variable.value = newValue },
                label = { Text(titleText) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = tint,
                    focusedLabelColor = tint,
                    focusedPlaceholderColor = tint,
                ),
                keyboardOptions = keyboardOptions
            )
        }
    }

    @Composable
    fun InputIntField(
        titleText: String,
        variable: MutableState<Int?>,
        outlined: Boolean = true,
        tint: Color = MaterialTheme.colorScheme.primary
    ) {
        val change: (String) -> Unit = {
                it ->
            var it1 = it
            it1 = it1.replace(",", ".").replace(".", "")

            variable.value = it1.toIntOrNull()
            if(variable.value == 0)
                variable.value = null
        }

        if (outlined)
            OutlinedTextField(
                value = if(variable.value == null) "" else variable.value.toString(),
                onValueChange = change,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = { Text(titleText) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = tint,
                    focusedTextColor = tint,
                    focusedLabelColor = tint,
                    focusedPlaceholderColor = tint,
                ),
            )
        else {
            TextField(
                value = if(variable.value == null) "" else variable.value.toString(),
                onValueChange = change,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = { Text(titleText) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = tint,
                    focusedLabelColor = tint,
                    focusedPlaceholderColor = tint,
                ),
            )
        }
    }

    @Composable
    fun LargeInputField(
        titleText: String,
        variable: MutableState<String>,
        outlined: Boolean = true,
        tint: Color = MaterialTheme.colorScheme.primary,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        visualTransformation: VisualTransformation = VisualTransformation.None
    ) {
        if (outlined)
            OutlinedTextField(
                value = variable.value,
                onValueChange = { newValue -> variable.value = newValue },
                label = { Text(titleText) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = tint,
                    focusedTextColor = tint,
                    focusedLabelColor = tint,
                    focusedPlaceholderColor = tint,
                ),
                keyboardOptions = keyboardOptions,
                visualTransformation = visualTransformation,
                modifier = Modifier
                    .height(256.dp)
            )
        else {
            TextField(
                value = variable.value,
                onValueChange = { newValue -> variable.value = newValue },
                label = { Text(titleText) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = tint,
                    focusedLabelColor = tint,
                    focusedPlaceholderColor = tint,
                ),
                keyboardOptions = keyboardOptions,
                modifier = Modifier
                    .height(256.dp)
            )
        }
    }

    @Composable
    fun BaseSurface(
        content: @Composable() (BoxScope.() -> Unit)
    ) {
        Surface {
            Box(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BaseScaffoldColumn(
        content: @Composable (() -> Unit),
        replaceColumn: @Composable (() -> Unit)? = null,
        titleText: String,
        onBackButtonClick: (() -> Unit)? = null,
        scrollState: ScrollState = rememberScrollState(),
        bringIntoViewRequester: BringIntoViewRequester? = null,
        backgroundColor: Color = Color.Transparent,
        floatingActionButton: @Composable (()->Unit) = {}
    ) {
        Scaffold(
            floatingActionButtonPosition = FabPosition.EndOverlay,
            floatingActionButton = floatingActionButton,
            containerColor =
                if (backgroundColor != Color.Transparent)
                    backgroundColor
                else
                    MaterialTheme.colorScheme.surface,
            contentWindowInsets = WindowInsets(16.dp, 16.dp, 16.dp, 64.dp),
            topBar = {
                TopAppBar(
                    expandedHeight = 64.dp,
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                    colors =
                        if (backgroundColor != Color.Transparent)
                            TopAppBarDefaults.topAppBarColors(
                                containerColor = backgroundColor,
                                scrolledContainerColor = backgroundColor,
                            )
                        else
                            TopAppBarDefaults.topAppBarColors(),
                    title = {
                        Text(
                            text = titleText,
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        if (onBackButtonClick != null) {
                            IconButton(
                                onClick = {
                                    presenter.onBackPressed()
                                },

                                ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                    contentDescription = ""
                                )
                            }
                        }
                    },
                )
            }) { padding ->
            Surface (
                color =
                    if (backgroundColor != Color.Transparent)
                        backgroundColor
                    else
                        MaterialTheme.colorScheme.surface
            ) {
                if (replaceColumn == null) {
                    Box(
                        modifier = if (bringIntoViewRequester != null)
                            Modifier
                                .bringIntoViewRequester(bringIntoViewRequester)
                                .padding(padding)
                                .fillMaxWidth()
                                .verticalScroll(scrollState)
                        else
                            Modifier
                                .padding(padding)
                                .fillMaxWidth()
                                .verticalScroll(scrollState),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            content()
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .padding(padding)
                            .padding(16.dp, 64.dp, 16.dp, 64.dp)
                    ) {
                        replaceColumn()
                    }
                }
            }
        }
    }

    @Composable
    fun LoadAsyncImage(
        modifier: Modifier = Modifier,
        model: Any?,
        contentDescription: String?,
        contentScale: ContentScale = ContentScale.Fit
    ) {
        SubcomposeAsyncImage(
            model = model,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale,
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(ShapeDefaults.Medium)
                        .background(MaterialTheme.colorScheme.surfaceContainer),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        )
    }


    @Composable
    fun CardTitleDescription(
        modifier: Modifier = Modifier,
        name: String,
        description: String,
        image: ImageBitmap? = null,
        imageLink: String = "",
        onClick: (() -> Unit)? = null,
        button: @Composable (() -> Unit) = {},
        backgroundColor: Color = Color.Unspecified,
        maxLines: Int = 4,
        contentScale: ContentScale = ContentScale.Fit
    ) {
        Column {
            Card(
                onClick = { onClick?.invoke() },
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors(
                    containerColor = backgroundColor
                )
            ) {
                Box {
                    Column {
                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxHeight()
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (image != null)
                                    Image(
                                        bitmap = image,
                                        contentDescription = "",
                                        contentScale = contentScale,
                                        alignment = Alignment.Center,
                                        modifier = Modifier
                                            .width(128.dp)
                                            .aspectRatio(1f)
                                            .clip(ShapeDefaults.ExtraSmall)
                                    )
                                else if(imageLink.isNotEmpty()) {
                                    LoadAsyncImage(
                                        model = imageLink,
                                        contentDescription = null,
                                        modifier = Modifier.width(128.dp).aspectRatio(1f).clip(ShapeDefaults.ExtraSmall),
                                        contentScale = contentScale
                                    )
                                }
                            }
                            VerticalDivider(thickness = 5.dp, color = Color.Transparent)
                            Column(
                                modifier = Modifier.weight(2f)
                            ) {
                                Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                Text(
                                    text = description,
                                    maxLines = maxLines,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        button()
                    }
                }
            }
            HorizontalDivider(thickness = 8.dp, color = Color.Transparent)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DatePickerDocked(
        datePickerState: DatePickerState,
        initialPickerVisibility: Boolean = false,
        titleText: String = ""
    ) {
        var showDatePicker by remember { mutableStateOf(initialPickerVisibility) }
        val selectedDate = datePickerState.selectedDateMillis?.let {
            convertMillisToDate(it)
        } ?: ""

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedDate,
                onValueChange = { },
                label = { Text("Дата записи") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = !showDatePicker }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Выберите дату"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            )

            if (showDatePicker) {
                Box(
                    modifier = Modifier
                        .padding(0.dp, 64.dp, 0.dp, 0.dp)
                ) {
                    Popup(
                        onDismissRequest = { showDatePicker = false },
                        alignment = Alignment.TopStart,
                        properties = PopupProperties(
                            clippingEnabled = true
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(elevation = 4.dp)
                                .background(MaterialTheme.colorScheme.surface)
                        ) {
                            DatePicker(
                                state = datePickerState,
                                showModeToggle = false,
                                title = {
                                    val modifier = Modifier.padding(
                                        PaddingValues(
                                            start = 24.dp,
                                            end = 12.dp,
                                            top = 16.dp
                                        )
                                    )
                                    when (datePickerState.displayMode) {
                                        DisplayMode.Input ->
                                            Text(text = "Введите дату:", modifier = modifier)

                                        DisplayMode.Picker ->
                                            Text(text = "Выберите дату:", modifier = modifier)
                                    }
                                },
                                headline = {
                                    Text(
                                        titleText,
                                        Modifier.padding(
                                            PaddingValues(
                                                start = 24.dp,
                                                end = 12.dp,
                                                bottom = 12.dp
                                            )
                                        )
                                    )
                                },
                                colors = DatePickerDefaults.colors(
                                    dayContentColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }

    @OptIn(ExperimentalMaterial3Api::class)
    class SelectableDatesList(val dates: Iterable<Int>) : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            if (Calendar.getInstance().timeInMillis > utcTimeMillis)
                return false

            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = utcTimeMillis

            return dates.contains(calendar.get(Calendar.DAY_OF_WEEK))
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DropdownCombined(
        expanded: MutableState<Boolean>,
        selectedOption: MutableState<String>,
        options: Iterable<String>,
        titleText: String
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded.value,
            onExpandedChange = {
                expanded.value = !expanded.value
            },
        ) {
            OutlinedTextField(
                label = { Text(titleText) },
                readOnly = true,
                value = selectedOption.value,
                onValueChange = {},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded.value
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
            )
            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = {
                    expanded.value = false
                }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(text = selectionOption) },
                        onClick = {
                            selectedOption.value = selectionOption
                            expanded.value = false
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun AppLogo() {
        Image(
            bitmap = ImageBitmap.imageResource(R.mipmap.ic_launcher_foreground),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(180.dp)
                .height(180.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
        )
    }
}