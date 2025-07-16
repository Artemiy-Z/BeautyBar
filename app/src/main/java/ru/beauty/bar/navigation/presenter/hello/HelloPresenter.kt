package ru.beauty.bar.navigation.presenter.hello

import ru.beauty.bar.App
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter
import androidx.core.content.edit

class HelloPresenter: BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }

    fun onBeginPress() {
        val sharedPrefs =
            App.INSTANCE.mainInterface.getSharedPrefs()
        sharedPrefs.edit() {
            putBoolean("FirstStart", false)
        }

        router.navigateTo(Screens.UserLoginScreen)
    }
}