package ru.beauty.bar.navigation.presenter.user.login;

import ru.beauty.bar.App
import ru.beauty.bar.database.api.AuthenticationRepository
import ru.beauty.bar.database.api.ClientFactory
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class UserLoginPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }

    fun onSignUpPressed() {
        router.navigateTo(Screens.UserSignUpScreen)
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
        val userResult = auth.logInCommonUser(login, password)

        if(userResult != null) {
            App.INSTANCE.sharedData.userType = 0
            App.INSTANCE.sharedData.userData = userResult
            onLoginSucceeded()
        }

        return Any()
    }

    fun onLoginSucceeded() {
        router.newRootScreen(Screens.UserMainScreen)
    }

    fun onAdminPressed() {
        router.navigateTo(Screens.AdminLoginScreen)
    }

    fun onMasterPressed() {
        router.navigateTo(Screens.MasterLoginScreen)
    }
}