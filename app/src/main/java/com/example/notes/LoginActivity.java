
package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {
    EditText email,pass;
    Button login_btn;
    TextView createAcc;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.emailreg);
        pass=findViewById(R.id.passtxt);
        login_btn=findViewById(R.id.login_btn);
        createAcc=findViewById(R.id.create_account_text_view_btn);
        progressbar=findViewById(R.id.progressbar);

        login_btn.setOnClickListener(v-> loginUser());
        createAcc.setOnClickListener((v)->startActivity(new Intent(LoginActivity.this,Register.class)));
    }
    void loginUser(){
        String  email1 =email.getText().toString();
        String passw =pass.getText().toString();

        boolean isValidated=validateDate(email1,passw);
        if(!isValidated){
            return;
        }
        loginAccountInFirebase(email1,passw);
    }

    void loginAccountInFirebase(String email,String password){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if(task.isSuccessful()){
                    //login sucess
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        //email verified
                    }else{
                        Utility.showToast(LoginActivity.this,"Email not verified, Please verify your email.");
                    }
                }else{
                    //login falied
                    Utility.showToast(LoginActivity.this,task.getException().getLocalizedMessage());
                }
            }
        });
    }

    void changeInProgress(boolean inProgress){
        if(inProgress){
            progressbar.setVisibility(View.VISIBLE);
            login_btn.setVisibility(View.GONE);
        }
        else{
            progressbar.setVisibility(View.GONE);
            login_btn.setVisibility(View.VISIBLE);
        }
    }
    boolean validateDate(String email1,String passw){
        //Validate the data are input by user.
        if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            email.setError("Email is invalid");
            return false;
        }
        if(passw.length()<6){
            pass.setError("Password length is invalid");
            return false;
        }
        return true;

    }
}