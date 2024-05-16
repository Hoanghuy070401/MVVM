package app.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import app.example.myapplication.MainActivity
import app.example.myapplication.R
import app.example.myapplication.databinding.FragmentAddNoteBinding
import app.example.myapplication.viewmodel.NoteViewModel
import app.example.myapplication.model.Note

class AddNoteFragment : Fragment(R.layout.fragment_add_note),MenuProvider {
    private var addNoteBinding : FragmentAddNoteBinding ? = null
    private val binding get() = addNoteBinding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var addNoteView: View



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       addNoteBinding = FragmentAddNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel = (activity as MainActivity).noteViewModel
        addNoteView = view

    }
    private fun saveNotes(view: View){
        val noteTile = binding.addNoteTitle.text.toString().trim()
        val noteDesc = binding.addNoteDesc.text.toString().trim()
        if (noteTile.isNotEmpty()){
            val note = Note(0, noteTile, noteDesc)
            notesViewModel.addNote(note)
            Toast.makeText(addNoteView.context, "Đã lưu ghi chú", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment,false)
        }else{
            Toast.makeText(addNoteView.context, "Hãy nhập tiêu đề trước khi lưu", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_notes,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
       return when(menuItem.itemId){
           R.id.saveMenu ->{
               saveNotes(addNoteView)
               true
           }
           else ->false
       }
    }

    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding = null
    }


}