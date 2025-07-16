package ru.beauty.bar.database.api

import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.UploadData
import io.github.jan.supabase.storage.storage
import io.ktor.util.date.getTimeMillis
import io.ktor.utils.io.jvm.javaio.toByteReadChannel
import io.ktor.utils.io.toByteArray
import ru.beauty.bar.App
import java.io.File
import kotlin.random.Random

class StorageRepository(val client: SupabaseClient) {
    suspend fun uploadImage(imageFile: File): String? {
        try {
            val filename = imageFile.name
            val byteArray = imageFile.inputStream().toByteReadChannel().toByteArray()

            val response = client.storage.from("img").upload(filename, byteArray)

            Log.d("Storage", response.key.toString())

            return client.storage.from("img").publicUrl(filename)
        } catch (e: Exception) {
            App.INSTANCE.mainInterface.showErrorMessage(message = e.localizedMessage)
            return null
        }
    }
}