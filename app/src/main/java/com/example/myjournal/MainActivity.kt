package com.example.myjournal

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.myjournal.adapter.NotesAdapter
import com.example.myjournal.database.NoteDatabase
import com.example.myjournal.databinding.ActivityMainBinding
import com.example.myjournal.models.Note
import com.example.myjournal.models.NoteViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
     lateinit var adapter: NotesAdapter
     lateinit var noteViewModel: NoteViewModel
    lateinit var noteSelected: Note


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        noteViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)



        }

    private fun initUI() {
        TODO("Not yet implemented")
    }
}
