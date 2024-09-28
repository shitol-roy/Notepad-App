package com.shiva.newnoteapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shiva.newnoteapp.R;
import com.shiva.newnoteapp.dao.NotesDao;
import com.shiva.newnoteapp.database.NotesDB;
import com.shiva.newnoteapp.databinding.ActivityNoteDetailsBinding;
import com.shiva.newnoteapp.entities.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteDetails extends AppCompatActivity {

    ActivityNoteDetailsBinding noteDetailsBinding ;
    String CreatedTime;
    int key , position;
    List<Notes> itemList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteDetailsBinding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(noteDetailsBinding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        key = intent.getIntExtra("ITEM_KEY", -1);
        position = intent.getIntExtra("ITEM_POSITION", -1);
        itemList = (List<Notes>) intent.getSerializableExtra("ITEM_LIST");
        CreatedTime = new SimpleDateFormat("dd MMMM yyyy" , Locale.getDefault()).format(new Date());

        if (itemList != null) {
            noteDetailsBinding.notetitle.setText(itemList.get(position).getNoteTitle());
            noteDetailsBinding.notebody.setText(itemList.get(position).getNoteBody());
        }

        noteDetailsBinding.backbtn.setOnClickListener(v ->{
            startActivity(new Intent(NoteDetails.this , MainActivity.class));
            finish();
        });

        noteDetailsBinding.savenote.setOnClickListener(v->{
            if(!(noteDetailsBinding.notetitle.getText().toString().isEmpty() || noteDetailsBinding.notebody.getText().toString().isEmpty())){

                noteDetailsBinding.savenote.setVisibility(View.GONE);
                SaveNotes();

            }else {
                Toast.makeText(this, "Enter Title and Body Correctly", Toast.LENGTH_SHORT).show();
            }
        });


    }

    void SaveNotes(){

        Notes notes = new Notes(noteDetailsBinding.notetitle.getText().toString() , noteDetailsBinding.notebody.getText().toString() , CreatedTime);

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            if (key != -1){
                NotesDB.getDB(NoteDetails.this).notesDao().DeleteNote(key);
                itemList.remove(position);
                NotesDB.getDB(getApplicationContext()).notesDao().AddNote(notes);
            }else {
                NotesDB.getDB(getApplicationContext()).notesDao().AddNote(notes);
            }


        });

        runOnUiThread(() -> {
            startActivity(new Intent(NoteDetails.this , MainActivity.class));
            finish();
        });

    }
}

