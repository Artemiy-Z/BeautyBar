package ru.beauty.bar.database.api

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

class ClientFactory {
    companion object fun getClient(): SupabaseClient
    {
        return createSupabaseClient(
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFueGZwc3pncmp6Y2p4dnRlZHRiIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDYzODQxNDMsImV4cCI6MjA2MTk2MDE0M30.H3dd8YNzf-wA7kPUvfZ0y3-YSsEzDtfjgA36_-XwBuM",
            supabaseUrl = "https://qnxfpszgrjzcjxvtedtb.supabase.co"
        ) {
            install(Postgrest)
            install(Storage)
        }
    }
}