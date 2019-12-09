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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG ="MainActivity";
    Button signUpButton;
    EditText emailText,passwordText,matchingPasswordText,phoneNunberText,addressText,nameText;
    TextView switchToSignIn;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    int counter=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sign Up Form");
        mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference("message");

        signUpButton=findViewById(R.id.signUpButton);
        emailText=findViewById(R.id.signUpEmailText);
        passwordText=findViewById(R.id.signUpPasswordText);
        switchToSignIn=findViewById(R.id.signInText);
        matchingPasswordText=findViewById(R.id.signUpMachingPasswordText);
        phoneNunberText=findViewById(R.id.signUpPhoneNumber);
        addressText=findViewById(R.id.signUpAddress);
        nameText=findViewById(R.id.signUpNameText);
        progressBar=findViewById(R.id.signUpProgressbar);


        switchToSignIn.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.signUpButton:
                userRegistration();
                break;

            case R.id.signInText:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }


    private boolean checkFields( String name,String email,String address,String password,String matchingPassword,String phoneNumber){

        if(name.isEmpty()){
            nameText.setError("Enter your name");
            nameText.requestFocus();
            return false;
        }

        if(email.isEmpty()){
            emailText.setError("Enter Your Email");
            emailText.requestFocus();
            return false;
        }

        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("Enter a Valid Email");
            emailText.requestFocus();
            return false;
        }

        if(password.isEmpty()){
            passwordText.setError("Enter Password");
            passwordText.requestFocus();
            return false;
        }
        if(password.length()<6 ){
            passwordText.setError("Minimum length is 6 characters");
            passwordText.requestFocus();
            return false;
        }
        if(!password.equals(matchingPassword)){
            matchingPasswordText.setError("Password does not match");
            matchingPasswordText.requestFocus();
            return false;
        }
        if(phoneNumber.isEmpty() || phoneNumber.length()<11){
            phoneNunberText.setError("Please provide valid phone number");
            phoneNunberText.requestFocus();
            return false;
        }
        if(address.isEmpty()){
            addressText.setError("Enter your address");
            addressText.requestFocus();
            return false;
        }
        return true;
    }

    private  void userRegistration(){

        final String email = emailText.getText().toString().trim();
        Log.d(TAG, "userRegistration: "+emailText);
        final String password =passwordText.getText().toString().trim();
        Log.d(TAG, "userRegistration: "+passwordText);
        String matchingPassword =matchingPasswordText.getText().toString().trim();
        final String phoneNumber =phoneNunberText.getText().toString().trim();
        final String address =addressText.getText().toString().trim();
        final String name =nameText.getText().toString().trim();


        if(checkFields(name,email,address,password,matchingPassword,phoneNumber) == false){

            counter--;
            if(counter==0){
                signUpButton.setEnabled(false);
                Toast.makeText(getApplicationContext(),"No Attempts remaining!! Please wait",Toast.LENGTH_SHORT).show();
            }

            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {


                            sendEmailVarificationLink();
                            String key =myRef.push().getKey();
                            UserData data =new UserData(name,email,password,phoneNumber,address);

                            myRef.child(key).setValue(data);



                        } else {

                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(),"User is already registered",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Error : "+task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }


                        }

                    }
                });
    }

    private void sendEmailVarificationLink(){

        FirebaseUser firebaseUser =mAuth.getInstance().getCurrentUser();

        if(firebaseUser !=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        Log.d(TAG, "signInWithEmail:success");
                        Toast.makeText(getApplicationContext(),"An email verification code is sent to your email",Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        finish();
                        Intent intent =new Intent(MainActivity.this,LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);


                    }else {
                        Toast.makeText(getApplicationContext(),"No verification code is send to your email",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}
