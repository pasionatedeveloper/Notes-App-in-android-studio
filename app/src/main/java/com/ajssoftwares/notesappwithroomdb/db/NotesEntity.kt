package com.ajssoftwares.notesappwithroomdb.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//POJO (Plain old Java Object)

@Entity(tableName = "notes_table")
data class NotesEntity(
    @ColumnInfo(name = "title")
    var notesTitle : String,
    @ColumnInfo(name = "description")
    var notesDescription : String)
{
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}