package com.k10.transferfiles.di

import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import com.k10.transferfiles.utils.Constants.CONFIG_PREFERENCE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {
    @Provides
    @Named("root_path")
    fun directoryRootPath(): String {
        return Environment.getExternalStorageDirectory().canonicalPath
    }

    @Provides
    @Named(CONFIG_PREFERENCE)
    fun getSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(CONFIG_PREFERENCE, Context.MODE_PRIVATE)
    }

}