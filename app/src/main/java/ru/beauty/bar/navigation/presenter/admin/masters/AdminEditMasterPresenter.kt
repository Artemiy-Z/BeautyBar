package ru.beauty.bar.navigation.presenter.admin.masters;

import android.net.Uri
import ru.beauty.bar.App
import ru.beauty.bar.dataLayer.grantAppAccess
import ru.beauty.bar.database.api.MasterRepository
import ru.beauty.bar.database.api.StorageRepository
import ru.beauty.bar.database.model.Master
import ru.beauty.bar.database.model.MasterInsert
import ru.beauty.bar.database.model.WorkSchedule
import ru.beauty.bar.database.model.WorkScheduleInsert
import ru.beauty.bar.navigation.presenter.BasePresenter

class AdminEditMasterPresenter : BasePresenter() {
    override fun onBackPressed() {
        App.INSTANCE.mainInterface.showChoiceMessage(
            message = "Отменить?",
            confirmCallback = {
                App.INSTANCE.sharedData.editedMaster = null
                router.exit()
            }
        )
    }

    fun onPictureSelectPressed(
        onSelectedListener: ((url: Uri?) -> Unit) = {},
    ) {
        App.INSTANCE.mainInterface.pickImage(
            callback = { uri ->
                onSelectedListener(uri)
            }
        )
    }

    override suspend fun uploadImage(imageUri: Uri): String? {
        val grantedFile = imageUri.grantAppAccess(App.INSTANCE.mainInterface.getContext())

        val storage = StorageRepository(App.INSTANCE.supabaseClient)

        val url = storage.uploadImage(imageFile = grantedFile)

        return url
    }

    suspend fun onUpdatePressed(
        source: Master,
        name: String,
        imageLink: String,
        imageUri: Uri?,
        workSchedule: WorkSchedule,
        experience: Int?
    ) {
        if(name.isEmpty())
        {
            App.INSTANCE.mainInterface.showErrorMessage("Не введено имя!")
            return
        }
        if(experience == null)
        {
            App.INSTANCE.mainInterface.showErrorMessage("Не введен опыт работы!")
            return
        }
        if(
            !workSchedule.monday &&
            !workSchedule.tuesday &&
            !workSchedule.wednesday &&
            !workSchedule.thursday &&
            !workSchedule.friday &&
            !workSchedule.saturday &&
            !workSchedule.sunday
            )
        {
            App.INSTANCE.mainInterface.showErrorMessage("Не указан график работы!")
            return
        }

        var imageLinkFinal = imageLink
        if(imageUri != null) {
            val url = uploadImage(imageUri = imageUri)
            if(url != null) {
                imageLinkFinal = url
            }
        }

        val master = Master(
            id = source.id,
            name = name,
            login = source.login,
            passhash = source.passhash,
            workScheduleId = source.workScheduleId,
            experience = experience,
            pictueLink = imageLinkFinal,
            portfloio = ""
        )

        val repository = MasterRepository(App.INSTANCE.supabaseClient)

        val result = repository.updateMaster(
            master = master,
            workSchedule = workSchedule
        )

        if(result != null) {
            onSaveSucceeded()
        }
    }

    suspend fun onInsertPressed(
        name: String,
        login: String,
        password: String,
        imageLink: String,
        imageUri: Uri?,
        workSchedule: WorkScheduleInsert,
        experience: Int?
    ) {
        if(name.isEmpty())
        {
            App.INSTANCE.mainInterface.showErrorMessage("Не введено имя!")
            return
        }
        if(login.isEmpty())
        {
            App.INSTANCE.mainInterface.showErrorMessage("Не введен логин!")
            return
        }
        if(password.isEmpty())
        {
            App.INSTANCE.mainInterface.showErrorMessage("Не введен пароль!")
            return
        }
        if(experience == null)
        {
            App.INSTANCE.mainInterface.showErrorMessage("Не введен опыт работы!")
            return
        }
        if(
            !workSchedule.monday &&
            !workSchedule.tuesday &&
            !workSchedule.wednesday &&
            !workSchedule.thursday &&
            !workSchedule.friday &&
            !workSchedule.saturday &&
            !workSchedule.sunday
        )
        {
            App.INSTANCE.mainInterface.showErrorMessage("Не указан график работы!")
            return
        }

        var imageLinkFinal = imageLink
        if(imageUri != null) {
            val url = uploadImage(imageUri = imageUri)
            if(url != null) {
                imageLinkFinal = url
            }
        }

        val master = MasterInsert(
            name = name,
            login = login,
            passhash = password.hashCode().toString(),
            workScheduleId = -1,
            experience = experience,
            pictueLink = imageLinkFinal,
            portfloio = ""
        )

        val repository = MasterRepository(App.INSTANCE.supabaseClient)

        val result = repository.insertMaster(
            master = master,
            workSchedule = workSchedule
        )

        if(result != null) {
            onSaveSucceeded()
        }
    }

    fun onSaveSucceeded() {
        App.INSTANCE.sharedData.editedMaster = null
        router.exit()
    }
}