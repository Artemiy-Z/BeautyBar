package ru.beauty.bar.dataLayer

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class FailedToEnableAccessForURI(override val message: String = "Failed to provide access.") :
    Exception()

fun Uri.grantAppAccess(context: Context): File {
    return try {
        // Get the file extension from the original Uri
        val fileExtension = context.contentResolver.getType(this)
            ?.let { MimeTypeMap.getSingleton().getExtensionFromMimeType(it) }
            ?: "tmp" // Default to "tmp" if the extension cannot be determined

        // Open an input stream for the URI (reads the content of the file)
        val inputStream = context.contentResolver.openInputStream(this)
            ?: throw FailedToEnableAccessForURI("Input stream is null.")

        // Create a temporary file in the app's cache directory with the correct extension
        val tempFile = File(context.cacheDir, "upload_${System.currentTimeMillis()}.$fileExtension")

        // Write the input stream's data to the temporary file
        FileOutputStream(tempFile).use { output ->
            inputStream.copyTo(output)
        }

        // Close the input stream after usage
        inputStream.close()

        // Generate a URI for the temp file using FileProvider
        tempFile

    } catch (e: Exception) {
        throw FailedToEnableAccessForURI().apply {
            this.stackTrace = e.stackTrace
        }
    }
}