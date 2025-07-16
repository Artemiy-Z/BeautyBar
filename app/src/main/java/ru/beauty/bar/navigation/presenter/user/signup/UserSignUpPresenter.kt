package ru.beauty.bar.navigation.presenter.user.signup;

import ru.beauty.bar.App
import ru.beauty.bar.database.api.AuthenticationRepository
import ru.beauty.bar.database.api.ClientFactory
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class UserSignUpPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }

    suspend fun signUp(
        login: String,
        password: String,
        passwordRepeat: String,
        name: String
    ) {
        val repo = AuthenticationRepository(App.INSTANCE.supabaseClient)

        val userResult = repo.signUpCommonUser(
            login = login,
            password = password,
            name = name
        )

        if(userResult != null) {
            App.INSTANCE.sharedData.userType = 0
            App.INSTANCE.sharedData.userData = userResult

            onSignUpSucceeded()
        }
    }

    fun onSignUpSucceeded() {
        router.newRootScreen(Screens.UserMainScreen)
    }
}