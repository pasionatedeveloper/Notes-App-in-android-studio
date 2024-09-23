package com.ajssoftwares.notesappwithroomdb

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajssoftwares.notesappwithroomdb.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    var notesList = ArrayList<NotesEntity>()

    lateinit var viewModel : NotesViewModel

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

            Log.d("android notes app", list?.size.toString())

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