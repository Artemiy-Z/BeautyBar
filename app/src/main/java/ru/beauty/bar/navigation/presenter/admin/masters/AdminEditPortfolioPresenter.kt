package ru.beauty.bar.navigation.presenter.admin.masters;

import android.net.Uri
import ru.beauty.bar.App
import ru.beauty.bar.dataLayer.UriUrl
import ru.beauty.bar.database.api.MasterRepository
import ru.beauty.bar.database.model.Master
import ru.beauty.bar.navigation.presenter.BasePresenter

class AdminEditPortfolioPresenter : BasePresenter() {
    override fun onBackPressed() {
        App.INSTANCE.mainInterface.showChoiceMessage(
            message = "Отменить?",
            confirmCallback = {
                App.INSTANCE.sharedData.editedMaster = null
                router.exit()
            }
        )
    }

    fun loadPortfolio(master: Master): List<UriUrl> {
        val stringSplit = master.portfloio.split("|")
        val portfolioList = List<UriUrl>(stringSplit.size) { index ->
            UriUrl(url = stringSplit[index])
        }

        return portfolioList
    }

    fun onMultipleImageSelectPressed(
        callback: ((List<Uri>)->Unit)
    ) {
        App.INSTANCE.mainInterface.pickMultipleImage(callback)
    }

    suspend fun onSavePressed(
        master: Master,
        portfolio: List<UriUrl>
    ) {
        if(portfolio.isEmpty()) {
            App.INSTANCE.mainInterface.showErrorMessage("Не выбрано ни одного изображения для загрузки!")
            return;
        }

        val urls: ArrayList<String> = ArrayList()
        for (
            item in portfolio
        ) {
            if(item.uri != null) {
                uploadImage(item.uri)?.let { urls.add(it) }
            }
            else if(item.url.isNotEmpty()) {
                urls.add(item.url)
            }
        }

        if(urls.isEmpty()) {
            App.INSTANCE.mainInterface.showErrorMessage("Произошла ошибка")
        }

        val repository = MasterRepository(App.INSTANCE.supabaseClient)

        val result = repository.updatePortfolio(
            master = master,
            portfolio = urls.joinToString(separator = "|")
        )
        if(result) {
            onSaveSucceeded()
        }
    }

    fun onSaveSucceeded() {
        App.INSTANCE.sharedData.editedMaster = null
        router.exit()
    }
}