package com.example.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.PlayerDao
import com.example.data.local.dao.ScoreDao
import com.example.data.local.dao.SettingsDao
import com.example.data.local.entity.ScoreEntity
import com.example.data.local.entity.SettingsEntity
import com.example.data.local.entity.PlayerEntity

@Database(
    entities = [ScoreEntity::class, SettingsEntity::class, PlayerEntity::class,
    ],
    version = 1,
    exportSchema = true
)
abstract class MemoryDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
    abstract fun settingsDao(): SettingsDao
    abstract fun playerDao(): PlayerDao
}
