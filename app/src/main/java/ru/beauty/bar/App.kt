package ru.beauty.bar

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import ru.beauty.bar.dataLayer.BookingOrder
import ru.beauty.bar.dataLayer.MasterScheduleCombined
import ru.beauty.bar.dataLayer.SearchPrompt
import ru.beauty.bar.database.api.ClientFactory
import ru.beauty.bar.database.model.Category
import ru.beauty.bar.database.model.Master
import ru.beauty.bar.database.model.Service
import ru.beauty.bar.database.model.User

class App: Application() {
    private val cicerone =  Cicerone.create()
    val router = cicerone.router
    val navigatorHolder = cicerone.getNavigatorHolder()
    lateinit var mainInterface: MainActivityInterface
    val sharedData = SharedData()
    val supabaseClient = ClientFactory().getClient()

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        internal lateinit var INSTANCE: App
            private set
    }

    class SharedData {
        var curCategory: Category? = null
        var curService: Service? = null
        var orderProgress: BookingOrder? = null
        var searchPrompt: SearchPrompt? = null
        var userType: Int? = null
        var userData: Any? = null
        var editedCategory: Category? = null
        var editedService: Service? = null
        var editedMaster: MasterScheduleCombined? = null
        var editedUser: User? = null
        var viewedPortfolio: String? = null
    }
}