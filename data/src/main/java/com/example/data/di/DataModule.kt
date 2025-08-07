package com.example.data.di

import android.app.Application
import androidx.room.Room
import com.example.data.local.dao.ScoreDao
import com.example.data.local.dao.SettingsDao
import com.example.data.local.db.MemoryDatabase
import com.example.data.remote.api.DragonBallApiService
import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.CardRepositoryImpl
import com.example.data.repository.ScoreRepositoryImpl
import com.example.data.repository.SettingsRepositoryImpl
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.CardRepository
import com.example.domain.repository.ScoreRepository
import com.example.domain.repository.SettingsRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    // --- ROOM DB ---
    @Provides
    @Singleton
    fun provideDatabase(app: Application): MemoryDatabase =
        Room.databaseBuilder(app, MemoryDatabase::class.java, "memory_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideScoreDao(db: MemoryDatabase): ScoreDao = db.scoreDao()

    @Provides
    @Singleton
    fun provideScoreRepository(scoreDao: ScoreDao): ScoreRepository =
        ScoreRepositoryImpl(scoreDao)

    // --- FIREBASE AUTH ---
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository =
        AuthRepositoryImpl(auth)

    // --- RETROFIT + API ---
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://dragonball-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideDragonBallApi(retrofit: Retrofit): DragonBallApiService =
        retrofit.create(DragonBallApiService::class.java)

    // --- CARD REPOSITORY ---
    @Provides
    @Singleton
    fun provideCardRepository(
        api: DragonBallApiService
    ): CardRepository = CardRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideSettingsDao(db: MemoryDatabase): SettingsDao = db.settingsDao()

    @Provides
    @Singleton
    fun provideSettingsRepository(dao: SettingsDao): SettingsRepository =
        SettingsRepositoryImpl(dao)
}
