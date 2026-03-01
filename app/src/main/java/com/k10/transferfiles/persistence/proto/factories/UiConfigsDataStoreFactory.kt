package com.k10.transferfiles.persistence.proto.factories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import com.k10.transferfiles.persistence.proto.Constants.UI_CONFIGS_PROTO_FILE_NAME
import com.k10.transferfiles.persistence.proto.defaults.UiConfigsDefault
import com.k10.transferfiles.persistence.proto.serializer.UiConfigsSerializer
import com.k10.transferfiles.proto.UiConfigs

fun provideUiConfigsDataStoreFactory(context: Context): DataStore<UiConfigs> {
    return DataStoreFactory.create(
        serializer = UiConfigsSerializer(),
        corruptionHandler = ReplaceFileCorruptionHandler {
            UiConfigsDefault
        },
        produceFile = {
            context.dataStoreFile(UI_CONFIGS_PROTO_FILE_NAME)
        }
    )
}