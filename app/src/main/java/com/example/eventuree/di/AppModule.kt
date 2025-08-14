package com.example.eventuree.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.eventuree.api.AuthApi
import com.example.eventuree.pref.PrefDatastore
import com.example.eventuree.pref.PrefDatastoreImpl
import com.example.eventuree.utils.Constants.BASE_URL
import com.example.eventuree.utils.Constants.EVENTURE_TOKENS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)  // Connection timeout
        .readTimeout(30, TimeUnit.SECONDS)     // Read timeout
        .writeTimeout(30, TimeUnit.SECONDS)    // Write timeout
        .build()

    @Singleton
    @Provides
    fun providesAuthApi(retrofitBuilder: Builder): AuthApi {
        return retrofitBuilder.build().create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Builder {
        return Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    @Singleton
    fun providesDatastore(@ApplicationContext context: Context): DataStore<Preferences>{
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData ={
                    emptyPreferences()
                }
            ), produceFile = {
                context.preferencesDataStoreFile(EVENTURE_TOKENS)
            }
        )
    }

    @Provides
    @Singleton
    fun providesDataPref(datastore: DataStore<Preferences>): PrefDatastore =
        PrefDatastoreImpl(datastore)

}
