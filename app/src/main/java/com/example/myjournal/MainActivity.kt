package com.example.myjournal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myjournal.adapter.NotesAdapter
import com.example.myjournal.database.NoteDatabase
import com.example.myjournal.databinding.ActivityMainBinding
import com.example.myjournal.models.Note
import com.example.myjournal.models.NoteViewModel

class MainActivity : AppCompatActivity(), NotesAdapter.notesClickListener, PopupMenu.OnMenuItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    lateinit var adapter: NotesAdapter
    lateinit var noteViewModel: NoteViewModel
    lateinit var noteSelected: Note
    private var notesList = ArrayList<Note>()

    private val updateNote =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.getParcelableExtra<Note>("note")?.let { note ->
                    noteViewModel.update(note)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        noteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)

        // Observe LiveData for note changes
        noteViewModel.allnotes.observe(this) { list ->
            list?.let {
                  // Add all new notes
                adapter.updateList(notesList)  // Update the adapter with the new list
            }
        }
        database = NoteDatabase.getDatabase(this)
    }

    private fun initUI() {
        // Set up RecyclerView with staggered grid layout
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)

        // Initialize adapter with empty notes list
        adapter = NotesAdapter(this, notesList, this)
        binding.recyclerview.adapter = adapter

        // Register activity result for adding a note
        val addNoteResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.getParcelableExtra<Note>("note")?.let { note ->
                    noteViewModel.insert(note)
                }
            }
        }

        // Launch AddNoteActivity when button is clicked
        binding.addnotebtn.setOnClickListener {
            val intent = Intent(this, Add_Note::class.java)
            addNoteResult.launch(intent)
        }

        // Set up search functionality
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filterList(newText ?: "")
                return true
            }
        })
    }

    override fun onItemClick(note: Note) {
        val intent = Intent(this, Add_Note::class.java)
        intent.putExtra("currentNote", note)
        updateNote.launch(intent)
    }

    override fun onLongItemClick(note: Note, cardView: CardView) {
        noteSelected = note
        popupDisplay(cardView)
    }

    private fun popupDisplay(cardView: CardView) {
        val popup = PopupMenu(this, cardView)
        popup.setOnMenuItemClickListener(this)
        popup.inflate(R.menu.popmenu)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete -> {
                noteViewModel.delete(noteSelected)
                true
            }
            else -> false
        }
    }
}
