package ru.beauty.bar.navigation.presenter.master.login;

import ru.beauty.bar.App
import ru.beauty.bar.database.api.AuthenticationRepository
import ru.beauty.bar.database.api.ClientFactory
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class MasterLoginPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }

    suspend fun login(
        login: String,
        password: String
    ): Any {
        if(login.isEmpty())
            return App.INSTANCE.mainInterface.showErrorMessage("Не введен логин!")

        if(password.isEmpty())
            return App.INSTANCE.mainInterface.showErrorMessage("Не введен пароль!")

        val auth = AuthenticationRepository(App.INSTANCE.supabaseClient)
        val userResult = auth.logInMaster(login, password)

        if(userResult != null) {
            App.INSTANCE.sharedData.userType = 2
            App.INSTANCE.sharedData.userData = userResult
            onLoginSucceeded()
        }

        return Any()
    }

    fun onLoginSucceeded() {
        router.newRootScreen(Screens.MasterMainScreen)
    }
}