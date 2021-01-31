package ru.moroz.developerslife.utils

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provider for resources
 */
@Singleton
class ResourceProvider @Inject constructor(private val context: Context) {
    fun getString(@StringRes resId: Int): String = context.getString(resId)
}