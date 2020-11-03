package org.binaryitplanet.aliceemarket.Utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap

class ConfigFunction {
    companion object {
        // Method to get file extension like .mp3, .jpg, .png
        fun getFileExtension(context: Context, imageUri: Uri): String? {
            val contentResolver: ContentResolver = context.contentResolver
            val mimeTypeMap = MimeTypeMap.getSingleton()
            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri))
        }
    }
}