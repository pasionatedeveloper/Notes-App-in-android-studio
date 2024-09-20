package com.ajssoftwares.notesappwithroomdb

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ajssoftwares.notesappwithroomdb.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val notesDAO = NotesDatabase.getDatabase(this).getNotesDao()
        val repo = NotesRepo(notesDAO)
        val factory = NotesVMFactory(repo)
        val viewModel = ViewModelProvider(this,factory).get(NotesViewModel::class.java)

        binding.fab.setOnClickListener {
            val title = binding.edtTitle.text.toString()
            val description = binding.edtDescription.text.toString()

            val notesEntity = NotesEntity(title, description)
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.insertNote(notesEntity)
            }

            Toast.makeText(this@MainActivity, "Note added", Toast.LENGTH_SHORT).show()
        }
    }
}