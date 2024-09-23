package com.ajssoftwares.notesappwithroomdb.ui.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajssoftwares.notesappwithroomdb.NotesAdapter
import com.ajssoftwares.notesappwithroomdb.db.NotesDatabase
import com.ajssoftwares.notesappwithroomdb.db.NotesEntity
import com.ajssoftwares.notesappwithroomdb.NotesRepo
import com.ajssoftwares.notesappwithroomdb.viewmodel.NotesVMFactory
import com.ajssoftwares.notesappwithroomdb.viewmodel.NotesViewModel
import com.ajssoftwares.notesappwithroomdb.R
import com.ajssoftwares.notesappwithroomdb.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private var notesList = ArrayList<NotesEntity>()

    private lateinit var viewModel : NotesViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.rvNotes.layoutManager = LinearLayoutManager(this)


        val notesDAO = NotesDatabase.getDatabase(this).getNotesDao()
        val repo = NotesRepo(notesDAO)
        val factory = NotesVMFactory(repo)
        viewModel = ViewModelProvider(this,factory)[NotesViewModel::class.java]

        viewModel.notesLiveData.observe(this){ list->

            if(list?.isNotEmpty() == true){
                notesList.clear()
                notesList.addAll(list)
                binding.rvNotes.adapter = NotesAdapter(this,notesList)
                binding.rvNotes.adapter?.notifyDataSetChanged()
            }
        }

        binding.fab.setOnClickListener {
            val dialog = Dialog(this)
            dialog.window?.setLayout(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
            dialog.setContentView(R.layout.add_new_note)

            val edtNoteTitle = dialog.findViewById<EditText>(R.id.edtNoteTitle)
            val edtNoteDescription = dialog.findViewById<EditText>(R.id.edtNoteDescription)
            val btnSaveNote = dialog.findViewById<Button>(R.id.btnSave)
            dialog.create()
            dialog.show()

            btnSaveNote.setOnClickListener {

                val title = edtNoteTitle.text.toString()
                val description = edtNoteDescription.text.toString()

                if(title.isEmpty() || description.isEmpty()){
                    Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                addNote(title,description)
                dialog.dismiss()
            }
        }
    }

    private fun addNote(title : String, description : String){
        Log.d("android notes app",  title+" "+description)
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.insertNote(NotesEntity(title,description))
        }
        Toast.makeText(this, "Note Added Successfully", Toast.LENGTH_SHORT).show()
    }
}