package com.k10.transferfiles.persistence.proto.managers

import androidx.datastore.core.DataStore
import com.k10.transferfiles.proto.UiConfigs
import kotlinx.coroutines.flow.first

class UiConfigsManager(
    private val uiConfigDataStore: DataStore<UiConfigs>
) {

    private lateinit var uiConfigs: UiConfigs

    suspend fun getConfig(forceRefresh: Boolean = false): UiConfigs {
        if (!uiConfigs.isInitialized || forceRefresh) {
            uiConfigs = uiConfigDataStore.data.first()
        }
        return uiConfigs
    }

    suspend fun updateSortType(type: UiConfigs.SortType) {
        uiConfigs = uiConfigDataStore.updateData {
            it.toBuilder().setSortType(type).build()
        }
    }

    suspend fun updateShowHiddenFiles(showHiddenFiles: Boolean) {
        uiConfigs = uiConfigDataStore.updateData {
            it.toBuilder().setShowHiddenFiles(showHiddenFiles).build()
        }
    }

    suspend fun updateShowInaccessibleFiles(showInaccessibleFiles: Boolean) {
        uiConfigs = uiConfigDataStore.updateData {
            it.toBuilder().setShowInaccessibleFiles(showInaccessibleFiles).build()
        }
    }

}