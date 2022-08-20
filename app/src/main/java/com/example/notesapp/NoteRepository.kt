package com.example.notesapp

import androidx.lifecycle.LiveData

class NoteRepository(private val notesDao: NotesDao) {
    val allNotes : LiveData<List<Note>> = notesDao.getAllNotes()

    suspend fun insert (note : Note){
        notesDao.insertNote(note)
    }

    suspend fun delete (note : Note){
        notesDao.deleteNote(note)
    }

    suspend fun update (note : Note){
        notesDao.updateNote(note)
    }
}