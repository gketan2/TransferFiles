package com.k10.transferfiles.models.mapper

import com.k10.transferfiles.proto.UiConfigs
import com.k10.transferfiles.utils.SortType

fun SortType.toProtoSortType() = when (this) {
    SortType.LAST_MODIFIED -> UiConfigs.SortType.LAST_MODIFIED
    SortType.SIZE -> UiConfigs.SortType.SIZE
    SortType.NAME -> UiConfigs.SortType.NAME
}

fun UiConfigs.SortType.toSortType() = when (this) {
    UiConfigs.SortType.LAST_MODIFIED -> SortType.LAST_MODIFIED
    UiConfigs.SortType.SIZE -> SortType.SIZE
    UiConfigs.SortType.NAME -> SortType.NAME
    UiConfigs.SortType.UNRECOGNIZED -> SortType.LAST_MODIFIED
}
