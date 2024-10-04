package com.example.myjournal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myjournal.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.Date

class Add_Note : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var note: com.example.myjournal.models.Note
    private lateinit var old_note: com.example.myjournal.models.Note
    var isUpdated= false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            old_note =
                intent.getSerializableExtra("currentNote") as com.example.myjournal.models.Note
            binding.etTitle.setText(old_note.title)
            binding.note.setText(old_note.note)
            isUpdated = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.imgCheck.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val note_desc = binding.note.text.toString()

            if (title.isNotEmpty() || note_desc.isNotEmpty()) {
                val formatter = SimpleDateFormat("EEE,d MMM yyyy, HH:mm a")

                if (isUpdated) {
                    val note= com.example.myjournal.models.Note (
                    old_note.id, title, note_desc, formatter.format(Date())
                    )
                } else{
                   val note= com.example.myjournal.models.Note(
                         null, title, note_desc, formatter.format(Date())
                    )
                }
                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            else{
               Toast.makeText(this@Add_Note, "Please add a note", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }
        binding.imgBack.setOnClickListener{
            onBackPressed()
        }

    }
    }
