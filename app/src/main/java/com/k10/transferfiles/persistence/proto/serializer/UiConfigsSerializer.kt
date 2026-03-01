package com.k10.transferfiles.persistence.proto.serializer

import androidx.datastore.core.Serializer
import com.k10.transferfiles.persistence.proto.defaults.UiConfigsDefault
import com.k10.transferfiles.proto.UiConfigs
import java.io.InputStream
import java.io.OutputStream

class UiConfigsSerializer : Serializer<UiConfigs> {
    override val defaultValue: UiConfigs
        get() = UiConfigsDefault

    override suspend fun readFrom(input: InputStream): UiConfigs {
        return UiConfigs.parseFrom(input)
    }

    override suspend fun writeTo(t: UiConfigs, output: OutputStream) {
        t.writeTo(output)
    }
}