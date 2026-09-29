package ru.beauty.bar.database.api

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import ru.beauty.bar.BuildConfig

class ClientFactory {
    companion object fun getClient(): SupabaseClient
    {
        return createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_PUBLIC_KEY
        ) {
            install(Postgrest)
            install(Storage)
        }
    }
}