package ru.beauty.bar.navigation.general

import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.beauty.bar.ui.fragment.EmptyFragment
import ru.beauty.bar.ui.fragment.admin.bookings.AdminBookings
import ru.beauty.bar.ui.fragment.admin.catalog_edit.CatalogEditOverview
import ru.beauty.bar.ui.fragment.admin.catalog_edit.categories.CatalogEditCategories
import ru.beauty.bar.ui.fragment.admin.catalog_edit.categories.EditCategory
import ru.beauty.bar.ui.fragment.admin.catalog_edit.services.CatalogEditServices
import ru.beauty.bar.ui.fragment.admin.catalog_edit.services.EditService
import ru.beauty.bar.ui.fragment.admin.login.AdminLogin
import ru.beauty.bar.ui.fragment.admin.main.AdminMain
import ru.beauty.bar.ui.fragment.admin.masters.AdminEditMaster
import ru.beauty.bar.ui.fragment.admin.masters.AdminEditPortfolio
import ru.beauty.bar.ui.fragment.admin.masters.AdminMasters
import ru.beauty.bar.ui.fragment.admin.users.AdminUsers
import ru.beauty.bar.ui.fragment.catalog.categories.CatalogCategories
import ru.beauty.bar.ui.fragment.catalog.search_results.CatalogSearch
import ru.beauty.bar.ui.fragment.catalog.service_item.CatalogService
import ru.beauty.bar.ui.fragment.catalog.single_category.CatalogCategory
import ru.beauty.bar.ui.fragment.hello.Hello
import ru.beauty.bar.ui.fragment.master.account.edit.MasterAccountEdit
import ru.beauty.bar.ui.fragment.master.account.view.MasterAccountView
import ru.beauty.bar.ui.fragment.master.bookings.MasterBookings
import ru.beauty.bar.ui.fragment.master.login.MasterLogin
import ru.beauty.bar.ui.fragment.master.main.MasterMain
import ru.beauty.bar.ui.fragment.user.account.edit.UserAccountEdit
import ru.beauty.bar.ui.fragment.user.account.view.UserAccountView
import ru.beauty.bar.ui.fragment.user.bookings.UserBookings
import ru.beauty.bar.ui.fragment.user.login.UserLogin
import ru.beauty.bar.ui.fragment.user.main.UserMain
import ru.beauty.bar.ui.fragment.user.order_make.date_select.OrderDateSelect
import ru.beauty.bar.ui.fragment.user.order_make.master_select.OrderMasterSelect
import ru.beauty.bar.ui.fragment.user.order_make.order_confirmation.OrderConfirm
import ru.beauty.bar.ui.fragment.user.order_make.master_select.OrderMasterPortfolioView
import ru.beauty.bar.ui.fragment.user.order_make.time_select.OrderTimeSelect
import ru.beauty.bar.ui.fragment.user.signup.UserSignUp

class Screens {
    object Empty: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = EmptyFragment()
    }
    object Hello: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = Hello()
    }

    object AdminBookingsScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = AdminBookings()
    }
    object AdminLoginScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = AdminLogin()
    }
    object AdminMainScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = AdminMain()
    }
    object AdminMastersScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = AdminMasters()
    }
    object AdminUsersScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = AdminUsers()
    }
    object CatalogCategoriesScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = CatalogCategories()
    }
    object CatalogSearchScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = CatalogSearch()
    }
    object CatalogServiceScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = CatalogService()
    }
    object CatalogCategoryScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = CatalogCategory()
    }
    object MasterAccountEditScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = MasterAccountEdit()
    }
    object MasterAccountViewScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = MasterAccountView()
    }
    object MasterBookingsScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = MasterBookings()
    }
    object MasterLoginScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = MasterLogin()
    }
    object MasterMainScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = MasterMain()
    }
    object UserAccountEditScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = UserAccountEdit()
    }
    object UserAccountViewScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = UserAccountView()
    }
    object UserBookingsScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = UserBookings()
    }
    object UserLoginScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = UserLogin()
    }
    object UserMainScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = UserMain()
    }
    object OrderDateSelectScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = OrderDateSelect()
    }
    object OrderMasterSelectScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = OrderMasterSelect()
    }
    object OrderConfirmScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = OrderConfirm()
    }
    object OrderTimeSelectScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = OrderTimeSelect()
    }
    object UserSignUpScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = UserSignUp()
    }

    object OrderMasterPortfolioViewScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = OrderMasterPortfolioView()
    }

    object CatalogEditOverviewScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = CatalogEditOverview()
    }

    object CatalogEditCategoriesScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = CatalogEditCategories()
    }

    object EditCategoryScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = EditCategory()
    }

    object CatalogEditServicesScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = CatalogEditServices()
    }

    object EditServiceScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = EditService()
    }

    object AdminEditMasterScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = AdminEditMaster()
    }

    object AdminEditPortfolioScreen: FragmentScreen {
        override fun createFragment(factory: FragmentFactory) = AdminEditPortfolio()
    }
}