package dev.beriashvili.assignments.shortstories.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.beriashvili.assignments.shortstories.entities.Story

@Dao
interface StoryDao {
    @Query("SELECT * FROM story")
    suspend fun getAll(): MutableList<Story>

    @Query("SELECT * FROM story WHERE id = :id")
    suspend fun getById(id: Int): Story

    @Query("SELECT * FROM story ORDER BY id DESC LIMIT 1")
    suspend fun getLastById(): Story

    @Insert
    suspend fun insert(story: Story)

    @Delete
    suspend fun delete(story: Story)

    @Query("UPDATE story SET title = :title, description = :description WHERE id = :id")
    suspend fun update(id: Int, title: String, description: String)
}
