package com.example.notesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.NoteItemBinding


class NoteAdapter(
    private val noteClickListener: NoteCLickListener,
    private val noteDeleteCLickListener: NoteDeleteCLickListener):
    RecyclerView.Adapter<ViewHolder>() {
    private val allNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
         return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.onBind(allNotes[position])

        holder.itemView.setOnClickListener {
            noteDeleteCLickListener.noteDelete(allNotes[position])
        }

        holder.itemView.setOnClickListener {
            noteClickListener.noteCLick(allNotes[position])
        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList : List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }


}

class ViewHolder( private val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root){
    fun onBind(note: Note){
        binding.noteItemTitle.text = note.noteTitle
        binding.noteItemTime.text = "Last updated" + note.timeStamp
    }
}



interface  NoteCLickListener {
    fun noteCLick(note:Note)
}

interface  NoteDeleteCLickListener {
     fun noteDelete(note:Note)
}