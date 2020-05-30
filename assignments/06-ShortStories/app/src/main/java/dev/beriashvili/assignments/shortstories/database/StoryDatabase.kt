package dev.beriashvili.assignments.shortstories.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.beriashvili.assignments.shortstories.entities.Story

@Database(entities = [Story::class], version = 1, exportSchema = false)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
}