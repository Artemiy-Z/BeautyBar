package ru.beauty.bar.database.api

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import ru.beauty.bar.App
import ru.beauty.bar.database.model.Admin
import ru.beauty.bar.database.model.Master
import ru.beauty.bar.database.model.User
import ru.beauty.bar.database.model.UserInsert

class AuthenticationRepository(val client: SupabaseClient) {
    suspend fun logInCommonUser(
        login: String,
        password: String
    ): User? {
        App.INSTANCE.mainInterface.toggleLoading(true)
        try {
            val passhash = password.hashCode()

            val users = client.postgrest
                .from("USER")
                .select()
                {
                    filter {
                        eq("login", login)
                    }
                }
                .decodeList<User>()

            App.INSTANCE.mainInterface.toggleLoading(false)
            if(users.isEmpty()) {
                App.INSTANCE.mainInterface.showErrorMessage("Пользователя с таким логином не существует!")
                return null
            }
            else {
                if (users[0].passhash == passhash.toString()) {
                    return users[0]
                } else {
                    App.INSTANCE.mainInterface.showErrorMessage("Неверный пароль!")
                    return null
                }
            }
        } catch (e: Exception) {
            App.INSTANCE.mainInterface.toggleLoading(false)
            Log.e("Auth","Runtime exception while log into common user" + e.localizedMessage)
            return null
        }
    }

    suspend fun signUpCommonUser(
        login: String,
        password: String,
        name: String,
    ): User? {
        App.INSTANCE.mainInterface.toggleLoading(true)
        try {
            val passhash = password.hashCode()

            val user = UserInsert(
                login = login,
                passhash = passhash.toString(),
                name = name,
                pictueLink = ""
            )

            val result = client.from("USER")
                .insert(user) {
                    select()
                }.decodeSingle<User>()

            Log.e("Auth", "created a user: $result")

            App.INSTANCE.mainInterface.toggleLoading(false)
            return result
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.toggleLoading(false)

            if(e.localizedMessage.contains("dupl")) {
                App.INSTANCE.mainInterface.showErrorMessage("Пользователь с таким логином уже существует!")
            }

            return null
        }
    }

    suspend fun logInAdmin(
        login: String,
        password: String
    ): Admin? {
        App.INSTANCE.mainInterface.toggleLoading(true)
        try {
            val passhash = password.hashCode()

            val users = client.postgrest
                .from("ADMIN")
                .select()
                {
                    filter {
                        eq("login", login)
                    }
                }
                .decodeList<Admin>()

            App.INSTANCE.mainInterface.toggleLoading(false)
            if(users.isEmpty()) {
                App.INSTANCE.mainInterface.showErrorMessage("Администратора с таким логином не существует!")
                return null
            }
            else {
                if (users[0].passhash == passhash.toString()) {
                    return users[0]
                } else {
                    App.INSTANCE.mainInterface.showErrorMessage("Неверный пароль!")
                    return null
                }
            }
        } catch (e: Exception) {
            App.INSTANCE.mainInterface.toggleLoading(false)
            Log.e("Auth","Runtime exception while log into common user" + e.localizedMessage)
            return null
        }
    }

    suspend fun logInMaster(
        login: String,
        password: String
    ): Master? {
        App.INSTANCE.mainInterface.toggleLoading(true)
        try {
            val passhash = password.hashCode()

            val users = client.postgrest
                .from("MASTER")
                .select()
                {
                    filter {
                        eq("login", login)
                    }
                }
                .decodeList<Master>()

            App.INSTANCE.mainInterface.toggleLoading(false)
            if(users.isEmpty()) {
                App.INSTANCE.mainInterface.showErrorMessage("Мастера с таким логином не существует!")
                return null
            }
            else {
                if (users[0].passhash == passhash.toString()) {
                    return users[0]
                } else {
                    App.INSTANCE.mainInterface.showErrorMessage("Неверный пароль!")
                    return null
                }
            }
        } catch (e: Exception) {
            App.INSTANCE.mainInterface.toggleLoading(false)
            Log.e("Auth","Runtime exception while log into common user" + e.localizedMessage)
            return null
        }
    }
}