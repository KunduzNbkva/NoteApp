package com.example.notesapp

import android.annotation.SuppressLint
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
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        getNote()
        addNoteClick()
    }

    @SuppressLint("SimpleDateFormat")
    private fun addNoteClick() {
        binding.addNote.setOnClickListener {
            val noteTitle = binding.addNoteTitle.text.toString()
            val noteDesc = binding.addNoteDescription.text.toString()

            if (noteType == resources.getString(R.string.edit)) {
                if (noteTitle.isNotEmpty() && noteDesc.isNotEmpty()) {
                    val sdf = SimpleDateFormat(resources.getString(R.string.date_pattern))
                    val currentDate: String = sdf.format(Date())
                    val editNote = Note(noteTitle, noteDesc, currentDate)
                    editNote.id = noteId
                    viewModel.updateNote(editNote)
                    Toast.makeText(this@NoteAddEditActivity,
                        resources.getText(R.string.note_edited), Toast.LENGTH_LONG)
                        .show()
                } else {
                    if (noteTitle.isNotEmpty() && noteDesc.isNotEmpty()) {
                        val sdf = SimpleDateFormat(resources.getString(R.string.date_pattern))
                        val currentDate: String = sdf.format(Date())
                        viewModel.addNote(Note(noteTitle, noteDesc, currentDate))
                        Toast.makeText(this@NoteAddEditActivity,
                            resources.getText(R.string.note_added), Toast.LENGTH_LONG)
                            .show()
                    }
                }
                startActivity(Intent(this@NoteAddEditActivity, MainActivity::class.java))
                this.finish()
            }
        }
    }

    private fun getNote() {
        noteType = intent.getStringExtra(MainActivity.ACTION_TYPE).toString()
        if (noteType == resources.getText(R.string.add_note)) {
            noteId = intent.getStringExtra(MainActivity.NOTE_ID)?.toInt() ?:-1
            binding.addNote.text = resources.getText(R.string.update_note)
            binding.addNoteTitle.setText(intent.getStringExtra(MainActivity.NOTE_TITLE))
            binding.addNoteDescription.setText(intent.getStringExtra(MainActivity.NOTE_DESCRIPTION))
        } else {
            binding.addNote.text = resources.getText(R.string.add_note)
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