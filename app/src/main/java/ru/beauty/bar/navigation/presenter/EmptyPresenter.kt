package ru.beauty.bar.navigation.presenter

import ru.beauty.bar.App

class EmptyPresenter: BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }
}