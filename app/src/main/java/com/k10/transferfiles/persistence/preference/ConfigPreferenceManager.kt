package com.k10.transferfiles.persistence.preference

import android.content.SharedPreferences
import com.k10.transferfiles.utils.Constants.CONFIG_PREFERENCE
import com.k10.transferfiles.utils.SortType
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class ConfigPreferenceManager @Inject constructor(
    @Named(CONFIG_PREFERENCE) private val sharedPreferences: SharedPreferences
) {

    private fun showHiddenFiles(): Boolean {
        return sharedPreferences.getBoolean(SHOW_HIDDEN_FILES, false)
    }

    fun setShowHiddenFiles(show: Boolean) {
        configInstance?.showHidden = show
        return sharedPreferences.edit() { putBoolean(SHOW_HIDDEN_FILES, show) }
    }

    private fun showInAccessibleFiles(): Boolean {
        return sharedPreferences.getBoolean(SHOW_INACCESSIBLE_FILES, true)
    }

    fun setShowInAccessibleFiles(show: Boolean) {
        configInstance?.showInAccessible = show
        return sharedPreferences.edit() { putBoolean(SHOW_INACCESSIBLE_FILES, show) }
    }

    fun setSortType(sortType: SortType) {
        configInstance?.sortingType = sortType
        sharedPreferences.edit() { putInt(SORT_TYPE, sortType.type) }
    }

    private fun getSortType(): SortType {
        return SortType.getByType(sharedPreferences.getInt(SORT_TYPE, 0))
    }

    fun getConfigurations(): ConfigPreference {
        if (configInstance == null)
            ConfigPreference.getInstance().apply {
                showHidden = showHiddenFiles()
                showInAccessible = showInAccessibleFiles()
                sortingType = getSortType()
            }
        return configInstance!!
    }

    data class ConfigPreference private constructor(
        var showHidden: Boolean = true,
        var showInAccessible: Boolean = true,
        var sortingType: SortType = SortType.LAST_MODIFIED
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
        private const val SORT_TYPE = "SORT_TYPE"
    }
}