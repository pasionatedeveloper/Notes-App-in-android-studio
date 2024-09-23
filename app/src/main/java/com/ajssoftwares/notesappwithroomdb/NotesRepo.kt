package com.ajssoftwares.notesappwithroomdb

import androidx.lifecycle.LiveData
import com.ajssoftwares.notesappwithroomdb.db.NotesDAO
import com.ajssoftwares.notesappwithroomdb.db.NotesEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesRepo(private val notesDAO: NotesDAO) {

    var notesLiveData : LiveData<List<NotesEntity>> = notesDAO.getNotes()

    suspend fun insertNote(notesEntity: NotesEntity){
        CoroutineScope(Dispatchers.IO).launch {
            notesDAO.insertNotes(notesEntity)
        }
    }
}