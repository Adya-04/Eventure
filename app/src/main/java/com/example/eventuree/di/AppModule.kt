package com.example.eventuree.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.eventuree.data.api.AuthApi
import com.example.eventuree.data.api.AuthInterceptor
import com.example.eventuree.data.api.MainApi
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

    @Singleton
    @Provides
    fun providesAuthApi(retrofitBuilder: Builder): AuthApi {
        return retrofitBuilder.build().create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun providesMainApi(retrofitBuilder: Builder, okHttpClient: OkHttpClient): MainApi{
        return retrofitBuilder.client(okHttpClient).build().create(MainApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Builder {
        return Builder()
            .baseUrl(BASE_URL)
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
