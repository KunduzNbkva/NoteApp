package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NoteCLickListener, NoteDeleteCLickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
    private val intentToEditAdd = Intent(this, NoteAddEditActivity::class.java)

    companion object{
        const val ACTION_TYPE = "actionType"
        const val NOTE_DESCRIPTION = "noteDescription"
        const val NOTE_ID = "noteID"
        const val NOTE_TITLE = "noteTitle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initRecyclerView()
        initFabButton()
    }

    private fun initFabButton() {
        binding.fabAddNote.setOnClickListener {
            startActivity(intentToEditAdd)
            this.finish()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(application)
        )[NoteViewModel::class.java]
        initRecyclerView()

        viewModel.allNotes.observe(this) { list ->
            list?.let {
                noteAdapter.updateList(it)
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        val noteAdapter = NoteAdapter(this, this)
        binding.rvNotes.adapter = noteAdapter
    }

    override fun noteCLick(note: Note) {
        intentToEditAdd.putExtra(ACTION_TYPE, R.string.edit)
        intentToEditAdd.putExtra(NOTE_TITLE, note.noteTitle)
        intentToEditAdd.putExtra(NOTE_DESCRIPTION, note.noteDescription)
        intentToEditAdd.putExtra(NOTE_ID, note.id)
        startActivity(intentToEditAdd)
        this.finish()
    }

    override fun noteDelete(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.noteTitle} deleted", Toast.LENGTH_LONG).show()
    }
}