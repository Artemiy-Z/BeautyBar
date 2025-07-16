package ru.beauty.bar.navigation.presenter.admin.masters;

import ru.beauty.bar.App
import ru.beauty.bar.dataLayer.MasterScheduleCombined
import ru.beauty.bar.database.api.MasterRepository
import ru.beauty.bar.database.model.Master
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class AdminMastersPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }

    suspend fun loadMasters(): List<MasterScheduleCombined> {
        val repository = MasterRepository(App.INSTANCE.supabaseClient)

        val list = repository.selectMasters()

        return list
    }

    fun onAddPressed() {
        App.INSTANCE.sharedData.editedMaster = null
        router.navigateTo(Screens.AdminEditMasterScreen)
    }

    fun onEditPressed(masterScheduleCombined: MasterScheduleCombined) {
        App.INSTANCE.sharedData.editedMaster = masterScheduleCombined
        router.navigateTo(Screens.AdminEditMasterScreen)
    }

    fun onEditPortfolioPressed(masterScheduleCombined: MasterScheduleCombined) {
        App.INSTANCE.sharedData.editedMaster = masterScheduleCombined
        router.navigateTo(Screens.AdminEditPortfolioScreen)
    }
}