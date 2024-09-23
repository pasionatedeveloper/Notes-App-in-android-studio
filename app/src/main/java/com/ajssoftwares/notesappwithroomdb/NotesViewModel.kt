package com.ajssoftwares.notesappwithroomdb

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(private var notesRepo: NotesRepo) : ViewModel() {


    val notesLiveData : LiveData<List<NotesEntity>> = notesRepo.notesLiveData

    suspend fun insertNote(notesEntity: NotesEntity) {

        viewModelScope.launch(Dispatchers.IO) {
            notesRepo.insertNote(notesEntity)
        }
    }
}