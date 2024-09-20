package com.ajssoftwares.notesappwithroomdb

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesRepo(private val notesDAO: NotesDAO) {

    suspend fun insertNote(notesEntity: NotesEntity){
        CoroutineScope(Dispatchers.IO).launch {
            notesDAO.insertNotes(notesEntity)
        }
    }
}