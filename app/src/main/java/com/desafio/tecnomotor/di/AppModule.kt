package com.desafio.tecnomotor.di

import android.content.Context
import androidx.room.Room
import com.desafio.tecnomotor.data.local.AppDatabase
import com.desafio.tecnomotor.data.remote.api.ApiService
import com.desafio.tecnomotor.util.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "tecnomotor_db"
        ).fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideMontadoraDao(db: AppDatabase) = db.montadoraDao()

    @Provides
    @Singleton
    fun provideVeiculoDao(db: AppDatabase) = db.veiculoDao()

    @Provides
    @Singleton
    fun provideNetworkUtils(@ApplicationContext context: Context): NetworkUtils {
        return NetworkUtils(context)
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
} 