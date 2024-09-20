package com.ajssoftwares.notesappwithroomdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(private var notesRepo: NotesRepo) : ViewModel() {


    suspend fun insertNote(notesEntity: NotesEntity) {

        viewModelScope.launch(Dispatchers.IO) {
            notesRepo.insertNote(notesEntity)
        }
    }
}