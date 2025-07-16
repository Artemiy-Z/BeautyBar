package ru.beauty.bar.navigation.presenter.admin.users;

import ru.beauty.bar.App
import ru.beauty.bar.database.api.UserRepository
import ru.beauty.bar.database.model.User
import ru.beauty.bar.navigation.presenter.BasePresenter

class AdminUsersPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }

    suspend fun getUserList(): List<User> {
        val repository = UserRepository(App.INSTANCE.supabaseClient)

        return repository.selectUsers()
    }
}