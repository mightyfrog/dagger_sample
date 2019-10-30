package org.mightyfrog.android.daggersample2

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideContext(application: App): Context = application.applicationContext

    @Singleton
    @Provides
    fun providesPreferenceStorage(context: Context): PreferenceStorage =
        SharedPreferenceStorage(context)
}
