package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText email,pass,conpass;
    Button createbtn;
    TextView regtxt;
    ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email=findViewById(R.id.emailreg);
        pass=findViewById(R.id.passtxt);
        conpass=findViewById(R.id.conpasstxt);
        createbtn=findViewById(R.id.create_ac_btn);
        regtxt=findViewById(R.id.login_text_view_btn);
        pbar=findViewById(R.id.progressbar);

        createbtn.setOnClickListener(v-> createAccount());
        regtxt.setOnClickListener(v-> finish());
    }
    void createAccount(){
        String  email1 =email.getText().toString();
        String passw =pass.getText().toString();
        String confirmpass =conpass.getText().toString();
        boolean isValidated=validateDate(email1,passw,confirmpass);
        if(!isValidated){
            return;
        }
        createAccountInFirebase(email1,passw);
    }
    void createAccountInFirebase(String email,String password){
        changeInProgress(true);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if(task.isSuccessful()){
                    Toast.makeText(Register.this,"Sucessfully Create account, Check email to verify",Toast.LENGTH_SHORT).show();
                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    firebaseAuth.signOut();
                    finish();
                }else{
                    Toast.makeText(Register.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    void changeInProgress(boolean inProgress){
        if(inProgress){
            pbar.setVisibility(View.VISIBLE);
            createbtn.setVisibility(View.GONE);
        }
        else{
            pbar.setVisibility(View.GONE);
            createbtn.setVisibility(View.VISIBLE);
        }
    }
    boolean validateDate(String email1,String passw,String confirmpass){
        //Validate the data are input by user.
        if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            email.setError("Email is invalid");
            return false;
        }
        if(passw.length()<6){
            pass.setError("Password length is inavlid");
            return false;
        }
        if(!passw.equals(confirmpass)){
            conpass.setError("Password not match!!");
            return  false;
        }
        return true;

    }
}