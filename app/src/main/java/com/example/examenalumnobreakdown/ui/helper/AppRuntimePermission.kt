package com.example.examenalumnobreakdown.ui.helper

import android.os.Build


data class AppRuntimePermission(
    val permission: String,
    val minSdk: Int = Build.VERSION_CODES.M // Por defecto, runtime desde M (23)
) {
    fun appliesToDevice(): Boolean = Build.VERSION.SDK_INT >= minSdk
}