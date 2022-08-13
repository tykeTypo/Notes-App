package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.Time;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.lang.annotation.Documented;

public class AddNote extends AppCompatActivity {

    EditText title,content;
    ImageButton savebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        title=findViewById(R.id.noteTxtTitle);
        content=findViewById(R.id.noteTxtCon);
        savebtn=findViewById(R.id.saveBtn);
        savebtn.setOnClickListener( (v)-> saveNote());
    }
    void saveNote(){
        String noteTitle=title.getText().toString();
        String noteContent=content.getText().toString();
        if (noteTitle==null || noteTitle.isEmpty()){
            title.setError("Title is Required");
            return;
        }
        Note note=new Note();
        note.setContent(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());
        saveNotetoFB(note);

    }
    void saveNotetoFB(Note note){
        DocumentReference documentReference;
        documentReference=Utility.getCollectionRefNote().document();
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utility.showToast(AddNote.this,"Note added Sucessfully");
                    finish();
                }else{
                    Utility.showToast(AddNote.this,"Failed while adding notes");
                }
            }
        });


    }
}