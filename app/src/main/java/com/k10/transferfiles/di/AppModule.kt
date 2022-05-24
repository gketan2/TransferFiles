package com.k10.transferfiles.di

import android.os.Environment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {
    @Provides
    @Named("root_path")
    fun directoryRootPath(): String {
        return Environment.getExternalStorageDirectory().canonicalPath
    }
}