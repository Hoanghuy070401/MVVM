package app.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import app.example.myapplication.model.Note
import app.example.myapplication.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(app:Application, private val noteRepository: NoteRepository):AndroidViewModel(app){
    fun addNote(note: Note)= viewModelScope.launch {
        noteRepository.insertNote(note)
    }
    fun deleteNote(note: Note)= viewModelScope.launch {
        noteRepository.deleteNote(note)
    }
    fun updateNote(note: Note)= viewModelScope.launch {
        noteRepository.updateNote(note)
    }

    fun searchNote(query: String?)=
        noteRepository.searchNotes(query)
    fun getAllNotes() = noteRepository.getAllNotes()


}