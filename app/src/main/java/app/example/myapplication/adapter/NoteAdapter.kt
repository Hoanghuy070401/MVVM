package app.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.example.myapplication.databinding.NoteLayoutBinding
import app.example.myapplication.fragments.HomeFragmentDirections
import app.example.myapplication.model.Note

class NoteAdapter:RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    class NoteViewHolder( var itemBinding : NoteLayoutBinding):RecyclerView.ViewHolder(itemBinding.root)

    private val differCallbacks = object :DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.noteTitle == newItem.noteTitle &&
                    oldItem.noteDesc == newItem.noteDesc
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem==newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallbacks)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
       return NoteViewHolder( NoteLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
       val currentNote = differ.currentList[position]
        holder.itemBinding.noteDesc.text= currentNote.noteDesc
        holder.itemBinding.noteTitle.text= currentNote.noteTitle
        holder.itemView.setOnClickListener{
            val direction =HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote)
            it.findNavController().navigate(direction)
        }
    }

}