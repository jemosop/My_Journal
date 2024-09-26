package com.example.myjournal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myjournal.R
import com.example.myjournal.databinding.ListItemBinding
import com.example.myjournal.models.Note
import kotlin.random.Random

class NotesAdapter(
    private val context: Context,
    private val notesList: ArrayList<Note>,
    val listener: notesClickListener) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val fullList: ArrayList<Note> = ArrayList(notesList)


    inner class NoteViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvTitle
        val notes = binding.tvNotes
        val date = binding.tvDate

        fun bind(note: Note) {
            title.text = note.title
            binding.tvTitle.isSelected = true
            notes.text = note.note
            date.text = note.date
            binding.tvDate.isSelected = true

            binding.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    randomColor()
                )
            )

            binding.cardView.setOnClickListener {
                listener.onItemClick(note)
            }


            binding.cardView.setOnLongClickListener {
                listener.onLongItemClick(note, binding.cardView)
                true
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun randomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.red)
        list.add(R.color.blue)
        list.add(R.color.pink)
        list.add(R.color.purple)
        list.add(R.color.magenta)
        list.add(R.color.orange)
        list.add(R.color.yellow)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notesList[position]

        holder.bind(note)
    }

    interface notesClickListener{
        fun onItemClick(note: Note)
        fun onLongItemClick(note: Note, cardView: CardView)
    }
}

