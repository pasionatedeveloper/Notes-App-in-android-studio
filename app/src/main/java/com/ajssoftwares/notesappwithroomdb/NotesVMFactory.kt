package com.ajssoftwares.notesappwithroomdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NotesVMFactory(private var repo: NotesRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(repo) as T
    }
}