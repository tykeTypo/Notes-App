package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class NoteDetailsActivity extends AppCompatActivity {
    EditText TitleTxt,ConTxt;
    Button saveNotebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        TitleTxt=findViewById(R.id.noteTitle);
        ConTxt=findViewById(R.id.ConTxt);
        saveNotebtn=findViewById(R.id.add_note_btn);
        saveNotebtn.setOnClickListener((v)-> saveNote());
    }
    void saveNote(){
        String Title=TitleTxt.getText().toString();
        String Content=ConTxt.getText().toString();

        if(Title==null || Title.isEmpty()){
            TitleTxt.setError("Title Required");
            return;
        }


    }
}