package com.example.data.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.local.dao.PlayerDao
import com.example.data.local.dao.ScoreDao
import com.example.data.local.dao.SettingsDao
import com.example.data.local.db.MemoryDatabase
import com.example.data.local.entity.PlayerEntity
import com.example.data.local.entity.ScoreEntity
import com.example.data.local.entity.SettingsEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsDaoTest {

    private lateinit var db: MemoryDatabase
    private lateinit var dao: SettingsDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MemoryDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.settingsDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertAndRetrieveSettings() = runTest {
        val settings = SettingsEntity(
            darkMode = true,
            sfxToggle = true,
            musicToggle = true,
            masterVolumeToggle = true,
            sfxVolume = 0.8f,
            musicVolume = 0.7f,
            masterVolume = 1.0f
        )

        dao.insertSettings(settings)
        val result = dao.getSettings()

        assertEquals(settings.darkMode, result?.darkMode)
        assertEquals(settings.sfxToggle, result?.sfxToggle)
        assertEquals(settings.musicToggle, result?.musicToggle)
        assertEquals(settings.masterVolumeToggle, result?.masterVolumeToggle)
        assertEquals(settings.sfxVolume, result?.sfxVolume)
        assertEquals(settings.musicVolume, result?.musicVolume)
        assertEquals(settings.masterVolume, result?.masterVolume)
    }
}

@RunWith(AndroidJUnit4::class)
class ScoreDaoTest {

    private lateinit var db: MemoryDatabase
    private lateinit var dao: ScoreDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MemoryDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.scoreDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertAndGetScores() = runTest {
        val score = ScoreEntity(
            bestScore = 0,
            bestTime = 300
        )

        dao.insertScore(score)
        val result = dao.getAllScores().first()

        assertEquals(1, result.size)
        assertEquals(score, result[0])
    }
}

@RunWith(AndroidJUnit4::class)
class PlayerDaoTest {

    private lateinit var db: MemoryDatabase
    private lateinit var dao: PlayerDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MemoryDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.playerDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertAndRetrievePlayer() = runTest {
        val player = PlayerEntity(
            email = "alex@example.com",
            name = "Alex",
            joinedDate = "2025-08-07",
            streak = 5,
            totalScore = 300
        )

        dao.insertPlayer(player)
        val result = dao.getPlayer("alex@example.com")

        assertEquals(player.email, result?.email)
        assertEquals(player.name, result?.name)
        assertEquals(player.joinedDate, result?.joinedDate)
        assertEquals(player.streak, result?.streak)
        assertEquals(player.totalScore, result?.totalScore)
    }
}
