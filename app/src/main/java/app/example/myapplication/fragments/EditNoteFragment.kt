package app.example.myapplication.fragments

import android.app.AlertDialog
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
import androidx.navigation.fragment.navArgs
import app.example.myapplication.MainActivity
import app.example.myapplication.R
import app.example.myapplication.databinding.FragmentEditNoteBinding
import app.example.myapplication.model.Note
import app.example.myapplication.viewmodel.NoteViewModel


class EditNoteFragment : Fragment(R.layout.fragment_edit_note),MenuProvider {

    private var editNoteBinding :FragmentEditNoteBinding ?= null
    private val binding get() = editNoteBinding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentsNotes: Note

    private val args: EditNoteFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editNoteBinding = FragmentEditNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel = (activity as MainActivity).noteViewModel
        currentsNotes = args.note!!

        binding.editNoteTitle.setText(currentsNotes.noteTitle)
        binding.editNoteDesc.setText(currentsNotes.noteDesc)

        binding.editNoteDesc.setOnClickListener {
            val noteTitle =  binding.editNoteTitle.text.toString().trim()
            val noteDesc =  binding.editNoteDesc.text.toString().trim()
            if (noteTitle.isNotEmpty()){
                val notes = Note(currentsNotes.id,noteTitle,noteDesc)
                notesViewModel.updateNote(notes)
                view.findNavController().popBackStack(R.id.homeFragment,false)

            }else{
                Toast.makeText(context, "Nhập tiêu đề ", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun deleteNotes(){
        AlertDialog.Builder(activity).apply {
            setTitle("Xóa ghi chú")
            setMessage("Bạn chắc chắn xóa ghi chú này chứ?")
            setPositiveButton("Xoá"){_,_ ->
                notesViewModel.deleteNote(currentsNotes)
                Toast.makeText(context, "Ghi chú đã xóa", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)

            }
            setNegativeButton("Hủy",null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId){
                R.id.deleteMenu->{
                    deleteNotes()
                    true

                }else -> false
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding= null
    }

}