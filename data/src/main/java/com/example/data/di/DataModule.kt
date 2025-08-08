package com.example.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.data.local.dao.PlayerDao
import com.example.data.local.dao.ScoreDao
import com.example.data.local.dao.SettingsDao
import com.example.data.local.db.MemoryDatabase
import com.example.data.remote.api.DragonBallApiService
import com.example.data.repository.CardRepositoryImpl
//import com.example.data.repository.AuthRepositoryImpl
//import com.example.data.repository.CardRepositoryImpl
//import com.example.data.repository.PlayerRepositoryImpl
//import com.example.data.repository.ScoreRepositoryImpl
//import com.example.data.repository.SettingsRepositoryImpl
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.CardRepository
import com.example.domain.repository.PlayerRepository
//import com.example.domain.repository.ScoreRepository
import com.example.domain.repository.SettingsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    // --- ROOM DB ---
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MemoryDatabase =
        Room.databaseBuilder(context, MemoryDatabase::class.java, "memory_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideScoreDao(db: MemoryDatabase): ScoreDao = db.scoreDao()

//    @Provides
//    @Singleton
//    fun provideScoreRepository(scoreDao: ScoreDao): ScoreRepository =
//        ScoreRepositoryImpl(scoreDao)
//
//    // --- FIREBASE AUTH ---
//    @Provides
//    @Singleton
//    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

//    @Provides
//    @Singleton
//    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository =
//        AuthRepositoryImpl(auth)

    // --- RETROFIT + API ---
    private const val BASE_URL = "https://dragonball-api.com/api/"

    @Provides @Singleton
    fun provideOkHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()


    @Provides
    @Singleton
    fun provideDragonBallApi(retrofit: Retrofit): DragonBallApiService =
        retrofit.create(DragonBallApiService::class.java)

//     --- CARD REPOSITORY ---
    @Provides
    @Singleton
    fun provideCardRepository(
        api: DragonBallApiService
    ): CardRepository = CardRepositoryImpl(api)

//    @Provides
//    @Singleton
//    fun provideSettingsDao(db: MemoryDatabase): SettingsDao = db.settingsDao()
//
//    @Provides
//    @Singleton
//    fun provideSettingsRepository(dao: SettingsDao): SettingsRepository =
//        SettingsRepositoryImpl(dao)
//
//    @Provides
//    @Singleton
//    fun providePlayerRepository(dao: PlayerDao): PlayerRepository =
//        PlayerRepositoryImpl(dao)
//
//    @Provides
//    @Singleton
//    fun providePlayerDao(db: MemoryDatabase): PlayerDao = db.playerDao()
//
}
