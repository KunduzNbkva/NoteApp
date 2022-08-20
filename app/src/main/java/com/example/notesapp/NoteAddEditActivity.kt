package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.databinding.ActivityNoteAddEditBinding
import java.text.SimpleDateFormat
import java.util.*

class NoteAddEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteAddEditBinding
    private lateinit var viewModel: NoteViewModel
    private lateinit var noteType: String
    private var noteId = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        getNote()
        addNoteClick()
    }

    private fun addNoteClick() {
        binding.addNote.setOnClickListener {
            val noteTitle = binding.addNoteTitle.text.toString()
            val noteDesc = binding.addNoteDescription.text.toString()

            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDesc.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd.MM.yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    val editNote = Note(noteTitle, noteDesc, currentDate)
                    editNote.id = noteId
                    viewModel.updateNote(editNote)
                    Toast.makeText(this@NoteAddEditActivity, "Note edited", Toast.LENGTH_LONG)
                        .show()
                } else {
                    if (noteTitle.isNotEmpty() && noteDesc.isNotEmpty()) {
                        val sdf = SimpleDateFormat("dd.MM.yyyy - HH:mm")
                        val currentDate: String = sdf.format(Date())
                        viewModel.addNote(Note(noteTitle, noteDesc, currentDate))
                        Toast.makeText(this@NoteAddEditActivity, "Note added", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                startActivity(Intent(this@NoteAddEditActivity, MainActivity::class.java))
                this.finish()
            }
        }
    }

    private fun getNote() {
        noteType = intent.getStringExtra("noteType").toString()
        if (noteType.equals("Edit")) {
            intent.getStringExtra("noteDescription")
            noteId = intent.getStringExtra("noteID")?.toInt() ?:-1
            binding.addNote.text = "Update note"
            binding.addNoteTitle.setText(intent.getStringExtra("noteTitle"))
            binding.addNoteDescription.setText(intent.getStringExtra("noteDescription"))
        } else {
            binding.addNote.text = "Add note"
        }
    }


    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(application)
        )[NoteViewModel::class.java]
    }
}