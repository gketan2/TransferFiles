package com.k10.transferfiles.utils

enum class SortType(var type: Int) {
    LAST_MODIFIED(0), SIZE(1), NAME(2);

    override fun toString(): String {
        return when (this.type) {
            2 -> "Name"
            1 -> "Size"
            0 -> "Last Modified"
            else -> "Last Modified"
        }
    }

    companion object {

        fun getByType(type: Int): SortType {
            return when (type) {
                2 -> NAME
                1 -> SIZE
                0 -> LAST_MODIFIED
                else -> LAST_MODIFIED
            }
        }

        private lateinit var sortOptions: ArrayList<SortType>
        fun getSortOptions(): ArrayList<SortType> {
            if (this::sortOptions.isInitialized.not()) sortOptions = arrayListOf(LAST_MODIFIED, SIZE, NAME)
            return sortOptions
        }
    }
}