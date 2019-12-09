
package com.example.projectdemo1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG="LoginActivity";
    Button loginButton;
    EditText email,password;
    TextView switchToSignUp,forgetPass;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    FirebaseUser user;
    int counter=5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Sign In Form");

        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        if(user !=null){
            finish();
            startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
        }


        loginButton=findViewById(R.id.loginButton);
        email=findViewById(R.id.loginEmailText);
        password=findViewById(R.id.loginPasswordText);
        switchToSignUp=findViewById(R.id.signUpText);
        progressBar=findViewById(R.id.loginProgressbar);
        forgetPass=findViewById(R.id.forgetPassword);

        switchToSignUp.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        forgetPass.setOnClickListener(this);

    }
    private boolean varifyEmail(){

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        Boolean emailFlag= user.isEmailVerified();

        if(emailFlag){
            startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
            return  true;
        }else{
            Toast.makeText(getApplicationContext(),"Please varify your mail",Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            return false;
        }


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                userLogin();
                break;
            case R.id.signUpText:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;

            case R.id.forgetPassword:
                intent = new Intent(getApplicationContext(),ForgetPassword.class);
                startActivity(intent);
                break;

        }
    }
    private  void userLogin(){

        counter--;
        if(counter==0){
            loginButton.setEnabled(false);
            Toast.makeText(getApplicationContext(),"No Attempts remaining!! Please wait",Toast.LENGTH_SHORT).show();
            return;
        }


        String emailText = email.getText().toString().trim();
        String passwordText =password.getText().toString().trim();

        if(emailText.isEmpty()){
            email.setError("Enter Your Email");
            email.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            email.setError("Enter a Valid Email");
            email.requestFocus();
            return;
        }

        if(passwordText.isEmpty()){
            password.setError("Enter Password");
            password.requestFocus();
            return;
        }
        if(passwordText.length()<6 ){
            password.setError("Minimum length is 6 characters");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            varifyEmail();

                            Log.d(TAG, "signInWithEmail:success");


                        } else {

                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Login Unsuccessful",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
