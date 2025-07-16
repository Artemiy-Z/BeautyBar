package ru.beauty.bar.database.api

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import ru.beauty.bar.App
import ru.beauty.bar.database.model.Category
import ru.beauty.bar.database.model.CategoryInsert
import ru.beauty.bar.database.model.Service
import ru.beauty.bar.database.model.ServiceInsert

class CatalogRepository(val client: SupabaseClient) {
    suspend fun selectCategories(): List<Category> {
        App.INSTANCE.mainInterface.toggleLoading(true)
        var list: List<Category> = emptyList()
        try {
            list = client
                .from("CATEGORY")
                .select()
                .decodeList<Category>()

            App.INSTANCE.mainInterface.toggleLoading(false)
            return list
        } catch (e: Exception) {
            App.INSTANCE.mainInterface.showErrorMessage(message = e.localizedMessage)

            App.INSTANCE.mainInterface.toggleLoading(false)
            return emptyList()
        }
    }

    suspend fun insertCategory(category: CategoryInsert): Category? {
        App.INSTANCE.mainInterface.toggleLoading(true)

        try {
            val result = client
                .from("CATEGORY")
                .insert(category) {
                    select()
                }.decodeSingle<Category>()

            App.INSTANCE.mainInterface.toggleLoading(false)
            return result
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.toggleLoading(false)

            if(e.localizedMessage.contains("dupl")) {
                App.INSTANCE.mainInterface.showErrorMessage("Категория уже существует!")
            }

            Log.e("Catalog", e.localizedMessage)

            return null
        }
    }

    suspend fun updateCategory(category: Category): Category? {
        App.INSTANCE.mainInterface.toggleLoading(true)

        try {
            val result = client
                .from("CATEGORY")
                .update(category) {
                    filter {
                        eq("category_id", category.id)
                    }
                    select()
                }.decodeSingle<Category>()

            Log.d("Catalog", result.toString())

            App.INSTANCE.mainInterface.toggleLoading(false)
            return result
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.toggleLoading(false)

            Log.e("Catalog", e.localizedMessage)

            return null
        }
    }

    suspend fun selectServices(category: Category): List<Service> {
        App.INSTANCE.mainInterface.toggleLoading(true)
        var list: List<Service> = emptyList()
        try {
            list = client
                .from("SERVICE")
                .select() {
                    filter {
                        eq("category_id", category.id)
                    }
                }
                .decodeList<Service>()

            App.INSTANCE.mainInterface.toggleLoading(false)
            return list
        } catch (e: Exception) {
            App.INSTANCE.mainInterface.showErrorMessage(message = e.localizedMessage)

            App.INSTANCE.mainInterface.toggleLoading(false)
            return emptyList()
        }
    }

    suspend fun insertService(service: ServiceInsert): Service? {
        App.INSTANCE.mainInterface.toggleLoading(true)

        try {
            val result = client
                .from("SERVICE")
                .insert(service) {
                    select()
                }.decodeSingle<Service>()

            App.INSTANCE.mainInterface.toggleLoading(false)
            return result
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.toggleLoading(false)

            if(e.localizedMessage.contains("dupl")) {
                App.INSTANCE.mainInterface.showErrorMessage("Услуга уже существует!")
            }

            Log.e("Catalog", e.localizedMessage)

            return null
        }
    }

    suspend fun updateService(service: Service): Service? {
        App.INSTANCE.mainInterface.toggleLoading(true)

        try {
            val result = client
                .from("SERVICE")
                .update(service) {
                    filter {
                        eq("service_id", service.id)
                    }
                    select()
                }

            Log.d("Catalog", result.toString())

            App.INSTANCE.mainInterface.toggleLoading(false)
            return result.decodeSingle<Service>()
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.toggleLoading(false)

            Log.e("CatalogError", e.localizedMessage)

            return null
        }
    }

    suspend fun deleteCategory(category: Category): Boolean {
        App.INSTANCE.mainInterface.toggleLoading(true)

        try {
            client
                .from("SERVICE")
                .delete() {
                    filter {
                        eq("category_id", category.id)
                    }
                }

            val result = client
                .from("CATEGORY")
                .delete() {
                    filter {
                        eq("category_id", category.id)
                    }
                }

            App.INSTANCE.mainInterface.toggleLoading(false)
            return true
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.toggleLoading(false)

            Log.e("CatalogError", e.localizedMessage)

            return false
        }
    }

    suspend fun deleteService(service: Service): Boolean {
        App.INSTANCE.mainInterface.toggleLoading(true)

        try {
            val result = client
                .from("SERVICE")
                .delete() {
                    filter {
                        eq("service_id", service.id)
                    }
                }

            App.INSTANCE.mainInterface.toggleLoading(false)
            return true
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.toggleLoading(false)

            Log.e("CatalogError", e.localizedMessage)

            return false
        }
    }

    suspend fun selectSingleCategory(categoryId: Int): Category? {
        App.INSTANCE.mainInterface.toggleLoading(true)

        try {
            val result = client
                .from("CATEGORY")
                .select {
                    filter {
                        eq("category_id", categoryId)
                    }
                }
                .decodeSingle<Category>()

            App.INSTANCE.mainInterface.toggleLoading(false)
            return result
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.toggleLoading(false)

            Log.e("CatalogError", e.localizedMessage)

            return null
        }
    }

    suspend fun selectSingleService(serviceId: Int): Service? {
        App.INSTANCE.mainInterface.toggleLoading(true)

        try {
            val result = client
                .from("SERVICE")
                .select {
                    filter {
                        eq("service_id", serviceId)
                    }
                }
                .decodeSingle<Service>()

            App.INSTANCE.mainInterface.toggleLoading(false)
            return result
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.toggleLoading(false)

            Log.e("CatalogError", e.localizedMessage)

            return null
        }
    }
}