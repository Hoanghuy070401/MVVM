package app.example.myapplication.repository

import androidx.room.Query
import app.example.myapplication.database.NoteDatabase
import app.example.myapplication.model.Note

class NoteRepository(private val db :NoteDatabase) {
    suspend fun insertNote(note: Note)=db.getNoteDao().insertNoteDao(note)
    suspend fun deleteNote(note: Note)=db.getNoteDao().deleteNoteDao(note)
    suspend fun updateNote(note: Note)=db.getNoteDao().updateNoteDao(note)

    fun getAllNotes()= db.getNoteDao().getAllNotes()
    fun searchNotes(query: String?)= db.getNoteDao().searchNotes(query)

}