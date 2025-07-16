package ru.beauty.bar.navigation.presenter.user.order_make.master_select;

import ru.beauty.bar.dataLayer.UriUrl
import ru.beauty.bar.database.model.Master
import ru.beauty.bar.navigation.presenter.BasePresenter

class OrderMasterPortfolioViewPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }

    fun loadPortfolio(portfolio: String): List<UriUrl> {
        val stringSplit = portfolio.split("|")
        val portfolioList = List<UriUrl>(stringSplit.size) { index ->
            UriUrl(url = stringSplit[index])
        }

        return portfolioList
    }
}