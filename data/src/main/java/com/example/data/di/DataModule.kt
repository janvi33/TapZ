package com.example.data.di

import android.app.Application
import androidx.room.Room
import com.example.data.local.dao.PlayerDao
import com.example.data.local.dao.ScoreDao
import com.example.data.local.dao.SettingsDao
import com.example.data.local.db.MemoryDatabase
import com.example.data.remote.api.DragonBallApiService
import com.example.data.repository.*
import com.example.domain.repository.*
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.example.domain.repository.ScoreRepository
import com.example.data.repository.ScoreRepositoryImpl




@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    // --- ROOM DATABASE ---
    @Provides
    @Singleton
    fun provideDatabase(app: Application): MemoryDatabase =
        Room.databaseBuilder(app, MemoryDatabase::class.java, "memory_database")
            .fallbackToDestructiveMigration()
            .build()

    // --- DAOs ---
    @Provides
    fun provideScoreDao(db: MemoryDatabase): ScoreDao = db.scoreDao()

    @Provides
    fun provideSettingsDao(db: MemoryDatabase): SettingsDao = db.settingsDao()

    @Provides
    fun providePlayerDao(db: MemoryDatabase): PlayerDao = db.playerDao()

    // --- REPOSITORIES ---
    @Provides
    @Singleton
    fun provideScoreRepository(scoreDao: ScoreDao): ScoreRepository =
        ScoreRepositoryImpl(scoreDao)

    @Provides
    @Singleton
    fun provideSettingsRepository(dao: SettingsDao): SettingsRepository =
        SettingsRepositoryImpl(dao)

    @Provides
    @Singleton
    fun providePlayerRepository(dao: PlayerDao): PlayerRepository =
        PlayerRepositoryImpl(dao)

    // --- FIREBASE AUTH ---
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository =
        AuthRepositoryImpl(auth)


    // --- RETROFIT ---
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://dragonball-api.com/api")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideDragonBallApi(retrofit: Retrofit): DragonBallApiService =
        retrofit.create(DragonBallApiService::class.java)

    @Provides
    @Singleton
    fun provideCardRepository(
        api: DragonBallApiService
    ): CardRepository = CardRepositoryImpl(api)


}





