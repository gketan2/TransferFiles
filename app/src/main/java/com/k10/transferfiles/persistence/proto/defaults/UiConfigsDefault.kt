package com.k10.transferfiles.persistence.proto.defaults

import com.k10.transferfiles.proto.UiConfigs

val UiConfigsDefault: UiConfigs by lazy {
    UiConfigs.newBuilder()
        .setShowHiddenFiles(false)
        .setShowInaccessibleFiles(true)
        .setSortType(UiConfigs.SortType.LAST_MODIFIED).build()
}