package com.k10.transferfiles.persistence.preference

import android.content.SharedPreferences
import com.k10.transferfiles.models.ConfigPreference
import com.k10.transferfiles.utils.Constants.CONFIG_PREFERENCE
import javax.inject.Inject
import javax.inject.Named

class ConfigPreferenceManager @Inject constructor(
    @Named(CONFIG_PREFERENCE) private val sharedPreferences: SharedPreferences,
    @Named(CONFIG_PREFERENCE) private val sharedPreferencesEditor: SharedPreferences.Editor
) {

    private fun showHiddenFiles(): Boolean {
        return sharedPreferences.getBoolean(SHOW_HIDDEN_FILES, false)
    }

    fun setShowHiddenFiles(show: Boolean) {
        return sharedPreferencesEditor.putBoolean(SHOW_HIDDEN_FILES, show).apply()
    }

    fun getConfigPreference(): ConfigPreference {
        return ConfigPreference(
            showHiddenFiles()
        )
    }

    companion object {
        private const val SHOW_HIDDEN_FILES = "SHOW_HIDDEN_FILES"
    }
}