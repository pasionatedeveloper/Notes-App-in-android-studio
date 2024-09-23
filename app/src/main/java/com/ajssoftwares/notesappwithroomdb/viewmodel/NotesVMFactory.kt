package com.ajssoftwares.notesappwithroomdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ajssoftwares.notesappwithroomdb.NotesRepo

class NotesVMFactory(private var repo: NotesRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(repo) as T
    }
}