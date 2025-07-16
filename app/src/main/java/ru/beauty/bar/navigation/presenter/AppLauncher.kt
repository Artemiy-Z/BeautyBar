package ru.beauty.bar.navigation.presenter

import com.github.terrakok.cicerone.Router
import ru.beauty.bar.navigation.general.Screens

class AppLauncher(private val router: Router){
    fun coldStart(firstStart: Boolean) {
        val rootScreen = if(firstStart) Screens.Hello else Screens.UserLoginScreen
        router.newRootChain(rootScreen)
    }
}