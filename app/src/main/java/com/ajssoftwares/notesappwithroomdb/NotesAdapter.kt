package com.ajssoftwares.notesappwithroomdb

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ajssoftwares.notesappwithroomdb.databinding.SingleNoteItemBinding

class NotesAdapter(var context : Context, var notesList : ArrayList<NotesEntity>) :

    RecyclerView.Adapter<NotesAdapter.NotesVH>() {

    class NotesVH(var binding : SingleNoteItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesVH {
        val view = SingleNoteItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return NotesVH(view)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NotesVH, position: Int) {
        holder.binding.noteTitle.text = notesList[position].notesTitle
        holder.binding.noteDescription.text = notesList[position].notesDescription
    }
}
