package app.example.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.example.myapplication.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)// Thay thế dữ liệu cũ bằng dữ liêụ mới nếu bị trùng khóa chính
    suspend fun insertNoteDao(note: Note)// thêm mới dữ liệu vào cơ sở dữ liệu

    @Update
    suspend fun updateNoteDao(note: Note)

    @Delete
    suspend fun deleteNoteDao(note: Note)

    @Query("SELECT * FROM NOTES ORDER BY id DESC")// truy vấn tất cả các dữ liệu và sắp xếp chúng
    fun getAllNotes():LiveData<List<Note>>

    @Query("SELECT * FROM NOTES WHERE noteTitle LIKE :query OR noteDesc LIKE :query")// truy vấn tất cả các trường dữ liệu Title có trường giống với query
    fun searchNotes(query: String?):LiveData<List<Note>>
}