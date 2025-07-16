package ru.beauty.bar.database.api

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import ru.beauty.bar.App
import ru.beauty.bar.database.model.User

class UserRepository(val client: SupabaseClient) {
    suspend fun selectUsers(): List<User> {
        App.INSTANCE.mainInterface.toggleLoading(true);
        try {
            val users = client
                .from("USER")
                .select()
                .decodeList<User>()

            App.INSTANCE.mainInterface.toggleLoading(false);
            return users
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.showErrorMessage(e.localizedMessage)

            App.INSTANCE.mainInterface.toggleLoading(false);
            return emptyList()
        }
    }

    suspend fun selectSingleUser(login: String): User? {
        App.INSTANCE.mainInterface.toggleLoading(true);
        try {
            val user = client
                .from("USER")
                .select() {
                    filter {
                        eq("login", login)
                    }
                }
                .decodeSingle<User>()

            App.INSTANCE.mainInterface.toggleLoading(false);
            return user
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.showErrorMessage(e.localizedMessage)

            App.INSTANCE.mainInterface.toggleLoading(false);
            return null
        }
    }

    suspend fun selectSingleUserById(Id: Int): User? {
        App.INSTANCE.mainInterface.toggleLoading(true);
        try {
            val user = client
                .from("USER")
                .select() {
                    filter {
                        eq("user_id", Id)
                    }
                }
                .decodeSingle<User>()

            App.INSTANCE.mainInterface.toggleLoading(false);
            return user
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.showErrorMessage(e.localizedMessage)

            App.INSTANCE.mainInterface.toggleLoading(false);
            return null
        }
    }

    suspend fun updateUser(user: User): User? {
        App.INSTANCE.mainInterface.toggleLoading(true);
        try {
            val user = client
                .from("USER")
                .update(user) {
                    filter {
                        eq("user_id", user.id!!)
                    }
                    select()
                }
                .decodeSingle<User>()

            App.INSTANCE.mainInterface.toggleLoading(false);
            return user
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.showErrorMessage("User "+e.localizedMessage)

            App.INSTANCE.mainInterface.toggleLoading(false);
            return null
        }
    }
}