package org.mightyfrog.android.daggersample2

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PreferenceStorage {
    val isEnabled: Boolean
}

@Singleton
class SharedPreferenceStorage @Inject constructor(context: Context) : PreferenceStorage {
    private val prefs: Lazy<SharedPreferences> = lazy {
        // Lazy to prevent IO access to main thread.
        context.applicationContext.getSharedPreferences(
            PREFS_NAME, Context.MODE_PRIVATE
        ).apply {
            registerOnSharedPreferenceChangeListener(changeListener)
        }
    }

    private val changeListener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
        when (key) {
            PREF_WORKING -> android.util.Log.d(
                "dagger sample",
                "$key: ${prefs.getBoolean(key, false)}"
            )
        }
    }

    override var isEnabled by BooleanPreference(prefs, PREF_WORKING, false)

    companion object {
        const val PREFS_NAME = "dagger_sample"
        const val PREF_WORKING = "pref_enabled"
    }
}

class BooleanPreference(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: Boolean
) : ReadWriteProperty<Any, Boolean> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        return preferences.value.getBoolean(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        preferences.value.edit { putBoolean(name, value) }
    }
}