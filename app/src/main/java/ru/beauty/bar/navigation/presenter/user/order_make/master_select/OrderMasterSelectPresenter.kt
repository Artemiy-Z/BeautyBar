package ru.beauty.bar.navigation.presenter.user.order_make.master_select;

import ru.beauty.bar.App
import ru.beauty.bar.dataLayer.MasterScheduleCombined
import ru.beauty.bar.database.api.MasterRepository
import ru.beauty.bar.database.model.Master
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class OrderMasterSelectPresenter : BasePresenter() {
    override fun onBackPressed() {
        App.INSTANCE.sharedData.orderProgress = null
        router.exit()
    }

    suspend fun loadMasters(): List<MasterScheduleCombined> {
        val repository = MasterRepository(App.INSTANCE.supabaseClient)

        val list = repository.selectMasters()

        return list
    }

    fun onDateSelectPressed(
        masterCombined: MasterScheduleCombined
    ) {
        App.INSTANCE.sharedData.orderProgress?.masterCombined = masterCombined
        router.navigateTo(Screens.OrderDateSelectScreen)
    }

    fun onMasterPortfolioPressed(portfolio: String) {
        App.INSTANCE.sharedData.viewedPortfolio = portfolio
        router.navigateTo(Screens.OrderMasterPortfolioViewScreen)
    }
}