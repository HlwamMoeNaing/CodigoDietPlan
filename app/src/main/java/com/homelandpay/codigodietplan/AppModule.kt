package com.homelandpay.codigodietplan

import android.content.Context
import com.homelandpay.codigodietplan.data.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context): PreferencesManager {
        return PreferencesManager(context)
    }

}