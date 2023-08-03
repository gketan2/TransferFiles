package com.k10.transferfiles.persistence.preference

import android.content.SharedPreferences
import com.k10.transferfiles.utils.Constants.CONFIG_PREFERENCE
import javax.inject.Inject
import javax.inject.Named

class ConfigPreferenceManager @Inject constructor(
    @Named(CONFIG_PREFERENCE) private val sharedPreferences: SharedPreferences
) {

    private fun showHiddenFiles(): Boolean {
        return sharedPreferences.getBoolean(SHOW_HIDDEN_FILES, false)
    }

    fun setShowHiddenFiles(show: Boolean) {
        configInstance?.showHidden = show
        return sharedPreferences.edit().putBoolean(SHOW_HIDDEN_FILES, show).apply()
    }

    private fun showInAccessibleFiles(): Boolean {
        return sharedPreferences.getBoolean(SHOW_INACCESSIBLE_FILES, true)
    }

    fun setShowInAccessibleFiles(show: Boolean) {
        configInstance?.showInAccessible = show
        return sharedPreferences.edit().putBoolean(SHOW_INACCESSIBLE_FILES, show).apply()
    }

    fun getConfigurations(): ConfigPreference {
        if (configInstance == null)
            ConfigPreference.getInstance().apply {
                showHidden = showHiddenFiles()
                showInAccessible = showInAccessibleFiles()
            }
        return configInstance!!
    }

    data class ConfigPreference private constructor(
        var showHidden: Boolean = true,
        var showInAccessible: Boolean = true
    ) {
        companion object {
            fun getInstance(): ConfigPreference {
                if (configInstance == null)
                    configInstance = ConfigPreference()
                return configInstance!!
            }
        }
    }

    companion object {
        private var configInstance: ConfigPreference? = null
        private const val SHOW_HIDDEN_FILES = "SHOW_HIDDEN_FILES"
        private const val SHOW_INACCESSIBLE_FILES = "SHOW_INACCESSIBLE_FILES"
    }
}