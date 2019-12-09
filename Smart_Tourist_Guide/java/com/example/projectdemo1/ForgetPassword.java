package com.example.projectdemo1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity implements View.OnClickListener {

    EditText forgetEmail;
    Button resetPassword;
    private FirebaseAuth mAuth;
    ProgressBar forgetProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forgetEmail=findViewById(R.id.forgetEmailText);
        resetPassword=findViewById(R.id.forgetButton);
        mAuth = FirebaseAuth.getInstance();
        resetPassword.setOnClickListener(this);



        forgetProgress=findViewById(R.id.forgetProgressbar);

    }



    @Override
    public void onClick(View v) {



        forgetProgress.setVisibility(View.VISIBLE);
        String email=forgetEmail.getText().toString().trim();

        if(email.equals("")){
            Toast.makeText(getApplicationContext(),"Please Enter valid Email",Toast.LENGTH_SHORT).show();
        }else{
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    forgetProgress.setVisibility(View.GONE);
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Reset password is send to your mail",Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(),"Error in sending password",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
//    private void closeKeyboard() {
//        InputMethodManager inputManager = (getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//
//    }
}
