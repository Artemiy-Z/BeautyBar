package ru.beauty.bar.navigation.presenter.user.account.edit

import android.net.Uri
import ru.beauty.bar.App
import ru.beauty.bar.database.api.UserRepository
import ru.beauty.bar.database.model.User
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class UserAccountEditPresenter: BasePresenter() {
    override fun onBackPressed() {
        App.INSTANCE.mainInterface.showChoiceMessage(
            message = "Отменить?",
            confirmCallback = {router.exit()})
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

    suspend fun onSavePressed(
        id: Int,
        login: String,
        name: String,
        passwordOld: String = "",
        passwordNew: String = "",
        passwordNewRepeat: String = "",
        imageLink: String = "",
        imageUri: Uri? = null
    ) {
        if(name.isEmpty()) {
            App.INSTANCE.mainInterface.showErrorMessage("Имя не может быть пустым!")
            return
        }

        if(passwordOld.isNotEmpty()) {
            if (passwordOld.hashCode() != (App.INSTANCE.sharedData.userData as User).passhash.toInt()) {
                App.INSTANCE.mainInterface.showErrorMessage("Старый пароль введен неверно!")
                return
            }

            if (passwordNew == passwordOld) {
                App.INSTANCE.mainInterface.showErrorMessage("Замена пароля должна отличаться от прошлого пароля!")
                return
            }

            if (passwordNew != passwordNewRepeat) {
                App.INSTANCE.mainInterface.showErrorMessage("Пароли должны совпадать!")
                return
            }
        }

        var finalLink = imageLink
        if(imageUri != null) {
            finalLink = uploadImage(imageUri = imageUri)?:finalLink
        }

        var passhashFinal = (App.INSTANCE.sharedData.userData as User).passhash

        if(passwordNew.isNotEmpty() && passwordOld.isNotEmpty()) {
            passhashFinal = passwordNew.hashCode().toString()
        }

        val repository = UserRepository(App.INSTANCE.supabaseClient)

        val userResult = repository.updateUser(
            user = User(
                id = id,
                name = name,
                passhash = passhashFinal,
                pictueLink = finalLink,
                login = login
            )
        )

        if(userResult != null) {
            App.INSTANCE.sharedData.userData = userResult
            router.exit()
        }
    }
}
