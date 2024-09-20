package com.ajssoftwares.notesappwithroomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface NotesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notesEntity: NotesEntity)

    @Delete
    suspend fun deleteNotes(notesEntity: NotesEntity)

    @Update
    suspend fun updateNotes(notesEntity: NotesEntity)

}