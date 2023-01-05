package com.example.ageprediction.ui.roomDB

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object MigrationDB {
    // Here we can write future migrations of out DB
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("")
        }
    }
}