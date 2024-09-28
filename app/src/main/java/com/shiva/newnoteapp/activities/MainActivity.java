package com.shiva.newnoteapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.shiva.newnoteapp.R;
import com.shiva.newnoteapp.adapter.NotesAdapter;
import com.shiva.newnoteapp.database.NotesDB;
import com.shiva.newnoteapp.databinding.ActivityMainBinding;
import com.shiva.newnoteapp.entities.Notes;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    List<Notes> allnotes;

    ActivityMainBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                allnotes =  NotesDB.getDB(MainActivity.this).notesDao().GetAllNotes();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkIfEmpty();
                    }
                });
            }
        }).start();

        binding.AddNote.setOnClickListener( v -> {
            startActivity(new Intent(this , NoteDetails.class));
            finish();
        });
    }

    public void onItemDeleted() {
        checkIfEmpty();
        binding.shownotes.getAdapter().notifyDataSetChanged();
    }

    private void checkIfEmpty() {
        if(allnotes.isEmpty()){
            binding.NoNoteslay.setVisibility(View.VISIBLE);
            binding.scrollView.setVisibility(View.GONE);
        }else {
            binding.NoNoteslay.setVisibility(View.GONE);
            binding.scrollView.setVisibility(View.VISIBLE);
            binding.shownotes.setAdapter(new NotesAdapter(allnotes));
        }
    }


}