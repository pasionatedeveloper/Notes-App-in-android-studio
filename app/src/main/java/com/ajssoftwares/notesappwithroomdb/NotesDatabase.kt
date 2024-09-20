package com.ajssoftwares.notesappwithroomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NotesEntity::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao() : NotesDAO

    companion object{
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): NotesDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                NotesDatabase::class.java,
                "notes_database").fallbackToDestructiveMigration().build()
        }
    }

}