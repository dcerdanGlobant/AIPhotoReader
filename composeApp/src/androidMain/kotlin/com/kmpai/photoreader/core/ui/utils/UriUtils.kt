package com.kmpai.photoreader.core.ui.utils

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import co.touchlab.kermit.Logger
import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.exif.ExifSubIFDDirectory
import com.drew.metadata.exif.GpsDirectory
import java.util.Date


object UriUtils {
    fun getBitmapFromUri(uri: Uri, contentResolver: ContentResolver): android.graphics.Bitmap? {
        return try {
            contentResolver.openInputStream(uri)?.use {
                return BitmapFactory.decodeStream(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getExifDate(uri: Uri, contentResolver: ContentResolver): Date? {
        return try {
            contentResolver.openInputStream(uri)?.use {
                val metadata = ImageMetadataReader.readMetadata(it)
                for (directory in metadata.directories) {
                    for (tag in directory.tags) {
                        Logger.i( "Metadata Tag: $tag")
                    }
                }
                val directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory::class.java)
                Logger.i( "Metadata Date: ${directory?.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)}")
                directory?.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)

            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getExifGPS(uri: Uri, contentResolver: ContentResolver): String? {
        return try {
            contentResolver.openInputStream(uri)?.use {
                val metadata = ImageMetadataReader.readMetadata(it)
                val gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory::class.java)
                val latitude = gpsDirectory?.geoLocation?.latitude
                val longitude = gpsDirectory?.geoLocation?.longitude
                Logger.i( "Metadata GPS: ${latitude.toString() + ", " + longitude.toString()}")
                return latitude.toString() + ", " + longitude.toString()
            }
        }catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}