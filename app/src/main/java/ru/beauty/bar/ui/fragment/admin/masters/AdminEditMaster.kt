package ru.beauty.bar.ui.fragment.admin.masters;

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
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
import ru.beauty.bar.database.model.WorkSchedule
import ru.beauty.bar.database.model.WorkScheduleInsert
import ru.beauty.bar.navigation.presenter.admin.masters.AdminEditMasterPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class AdminEditMaster : BaseFragment() {
    override val presenter = AdminEditMasterPresenter()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ComposeFunction() {
        val scope = rememberCoroutineScope()

        loadingBackground = MaterialTheme.colorScheme.tertiaryContainer
        loadingColor = Color.White

        val isEditing = App.INSTANCE.sharedData.editedMaster != null

        val masterName = remember { mutableStateOf("") }
        val masterLogin = remember { mutableStateOf("") }
        val masterPassword = remember { mutableStateOf("") }
        val masterImageLink = remember { mutableStateOf("") }
        val masterImageUri = remember { mutableStateOf<Uri?>(null) }
        val masterExperience = remember { mutableStateOf<Int?>(null) }

        val daysOfWeek = remember {
            arrayOf(
                "Понедельник",
                "Вторник",
                "Среда",
                "Четверг",
                "Пятница",
                "Суббота",
                "Воскресенье",
            )
        }

        val workScheduleSelected = remember {
            mutableStateListOf<String>()
        }

        if (isEditing) {
            masterName.value = App.INSTANCE.sharedData.editedMaster?.master!!.name
            masterLogin.value = App.INSTANCE.sharedData.editedMaster?.master!!.login
            masterImageLink.value = App.INSTANCE.sharedData.editedMaster?.master!!.pictueLink
            masterExperience.value = App.INSTANCE.sharedData.editedMaster?.master!!.experience
            if(App.INSTANCE.sharedData.editedMaster?.schedule!!.monday)
                workScheduleSelected.add(daysOfWeek.toList()[0])
            if(App.INSTANCE.sharedData.editedMaster?.schedule!!.tuesday)
                workScheduleSelected.add(daysOfWeek.toList()[1])
            if(App.INSTANCE.sharedData.editedMaster?.schedule!!.wednesday)
                workScheduleSelected.add(daysOfWeek.toList()[2])
            if(App.INSTANCE.sharedData.editedMaster?.schedule!!.thursday)
                workScheduleSelected.add(daysOfWeek.toList()[3])
            if(App.INSTANCE.sharedData.editedMaster?.schedule!!.friday)
                workScheduleSelected.add(daysOfWeek.toList()[4])
            if(App.INSTANCE.sharedData.editedMaster?.schedule!!.saturday)
                workScheduleSelected.add(daysOfWeek.toList()[5])
            if(App.INSTANCE.sharedData.editedMaster?.schedule!!.sunday)
                workScheduleSelected.add(daysOfWeek.toList()[6])
        }

        BaseScaffoldColumn(
            titleText =
                if (isEditing)
                    "Редактирование мастера"
                else
                    "Добавление мастера",
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            onBackButtonClick = { presenter.onBackPressed() },
            content = {
                var model: Any? = null
                if (masterImageUri.value != null) {
                    model = masterImageUri.value
                } else if (masterImageLink.value.isNotEmpty()) {
                    model = masterImageLink.value
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
                        presenter.onPictureSelectPressed(
                            onSelectedListener = { uri: Uri? ->
                                masterImageUri.value = uri
                            }
                        )
                    }
                ) { Text("Загрузить фото профиля") }

                HorizontalDivider(
                    thickness = 32.dp,
                    color = Color.Transparent
                )

                InputField(
                    titleText = "Имя",
                    variable = masterName,
                    tint = Color.White
                )

                if (!isEditing) {
                    InputField(
                        titleText = "Логин",
                        variable = masterLogin,
                        tint = Color.White
                    )
                    InputField(
                        titleText = "Пароль",
                        variable = masterPassword,
                        tint = Color.White
                    )
                }

                val expanded = remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expanded.value,
                    onExpandedChange = {
                        expanded.value = !expanded.value
                    },
                ) {
                    OutlinedTextField(
                        label = { Text("Категория") },
                        readOnly = true,
                        value = workScheduleSelected.let {
                            val string = scheduleToString(
                                monday = it.contains(daysOfWeek[0]),
                                tuesday = it.contains(daysOfWeek[1]),
                                wednesday = it.contains(daysOfWeek[2]),
                                thursday = it.contains(daysOfWeek[3]),
                                friday = it.contains(daysOfWeek[4]),
                                saturday = it.contains(daysOfWeek[5]),
                                sunday = it.contains(daysOfWeek[6]),
                            )
                            if (string.isNotEmpty()) {
                                string
                            } else {
                                "(Укажите дни работы)"
                            }
                        },
                        onValueChange = {},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded.value
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                            focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                            focusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                            focusedTrailingIconColor = MaterialTheme.colorScheme.onTertiary,
                            focusedBorderColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onTertiary,
                        ),
                        modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                    )
                    ExposedDropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = {
                            expanded.value = false
                        },
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ) {
                        daysOfWeek.toList().forEach { selectionOption ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = selectionOption
                                        )
                                        Checkbox(
                                            checked = workScheduleSelected.contains(selectionOption),
                                            onCheckedChange = { isChecked ->
                                                if(isChecked)
                                                    workScheduleSelected.add(selectionOption)
                                                else
                                                    workScheduleSelected.remove(selectionOption)
                                            }
                                        )
                                    }
                                },
                                onClick = {},
                                colors = MenuDefaults.itemColors(
                                    textColor = MaterialTheme.colorScheme.onTertiary,
                                )
                            )
                        }
                    }
                }

                InputIntField(
                    titleText = "Опыт работы",
                    variable = masterExperience,
                    tint = Color.White
                )

                Button(
                    onClick = {
                        scope.launch {
                            if (isEditing) {
                                presenter.onUpdatePressed(
                                    source = App.INSTANCE.sharedData.editedMaster?.master!!,
                                    name = masterName.value,
                                    imageLink = masterImageLink.value,
                                    imageUri = masterImageUri.value,
                                    workSchedule = WorkSchedule(
                                        id = App.INSTANCE.sharedData.editedMaster?.schedule!!.id,
                                        monday = workScheduleSelected.contains(daysOfWeek[0]),
                                        tuesday = workScheduleSelected.contains(daysOfWeek[1]),
                                        wednesday = workScheduleSelected.contains(daysOfWeek[2]),
                                        thursday = workScheduleSelected.contains(daysOfWeek[3]),
                                        friday = workScheduleSelected.contains(daysOfWeek[4]),
                                        saturday = workScheduleSelected.contains(daysOfWeek[5]),
                                        sunday = workScheduleSelected.contains(daysOfWeek[6]),
                                    ),
                                    experience = masterExperience.value
                                )
                            } else {
                                presenter.onInsertPressed(
                                    name = masterName.value,
                                    login = masterLogin.value,
                                    password = masterPassword.value,
                                    imageLink = masterImageLink.value,
                                    imageUri = masterImageUri.value,
                                    workSchedule = WorkScheduleInsert(
                                        monday = workScheduleSelected.contains(daysOfWeek[0]),
                                        tuesday = workScheduleSelected.contains(daysOfWeek[1]),
                                        wednesday = workScheduleSelected.contains(daysOfWeek[2]),
                                        thursday = workScheduleSelected.contains(daysOfWeek[3]),
                                        friday = workScheduleSelected.contains(daysOfWeek[4]),
                                        saturday = workScheduleSelected.contains(daysOfWeek[5]),
                                        sunday = workScheduleSelected.contains(daysOfWeek[6]),
                                    ),
                                    experience = masterExperience.value
                                )
                            }
                        }
                    }
                ) { Text("Сохранить изменения") }
            }
        )
    }

    fun scheduleToString(
        monday: Boolean,
        tuesday: Boolean,
        wednesday: Boolean,
        thursday: Boolean,
        friday: Boolean,
        saturday: Boolean,
        sunday: Boolean,
    ): String {
        var string = ""
        if (monday)
            string += "Пн,"
        if (tuesday)
            string += "Вт,"
        if (wednesday)
            string += "Ср,"
        if (thursday)
            string += "Чт,"
        if (friday)
            string += "Пт,"
        if (saturday)
            string += "Сб,"
        if (sunday)
            string += "Вс,"
        return string.trimEnd(',')
    }
}