package org.mightyfrog.android.daggersample2

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity
}