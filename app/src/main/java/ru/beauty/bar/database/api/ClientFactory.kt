package ru.beauty.bar.database.api

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

class ClientFactory {
    companion object fun getClient(): SupabaseClient
    {
        return createSupabaseClient(
            supabaseUrl = "https://wxixfmpszxqqsryrwwky.supabase.co",
            supabaseKey = "sb_publishable_ojoe6a_tRElGtMZXvEprQA_g0yTI-b4"
        ) {
            install(Postgrest)
            install(Storage)
        }
    }
}