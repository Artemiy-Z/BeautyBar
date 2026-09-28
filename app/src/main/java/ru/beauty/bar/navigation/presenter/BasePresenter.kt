package ru.beauty.bar.navigation.presenter

import com.github.terrakok.cicerone.Router
import ru.beauty.bar.App

abstract class BasePresenter {
    open fun onBackPressed() {router.exit()}
    val router: Router
        get() = App.INSTANCE.router
}