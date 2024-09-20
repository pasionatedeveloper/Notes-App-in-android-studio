package com.ajssoftwares.notesappwithroomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

//POJO (Plain old Java Object)

@Entity(tableName = "notes_table")
data class NotesEntity(var notesTitle : String, var notesDescription : String)
{
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}