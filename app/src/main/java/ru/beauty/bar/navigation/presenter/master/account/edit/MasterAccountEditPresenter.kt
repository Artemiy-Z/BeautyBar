package ru.beauty.bar.navigation.presenter.master.account.edit;

import ru.beauty.bar.App
import ru.beauty.bar.navigation.presenter.BasePresenter

class MasterAccountEditPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }
}