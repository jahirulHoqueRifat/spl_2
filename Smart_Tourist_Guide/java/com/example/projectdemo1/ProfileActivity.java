package com.example.projectdemo1;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileNotFoundException;

public class ProfileActivity extends AppCompatActivity{

    private static final String TAG = "ProfileActivity";

    FirebaseAuth firebaseAuth;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    EditText device_location,input_search;

    private GridView gridView;
    private String[] types;
    private int[] flags= {R.drawable.top_picks,R.drawable.restaurent,R.drawable.sea_beach,
            R.drawable.park,R.drawable.zoo, R.drawable.cafe,R.drawable.cinema,R.drawable.museum,R.drawable.hill,
            R.drawable.waterfall,R.drawable.resort, R.drawable.recommandation};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        device_location=findViewById(R.id.device_Location);
        input_search=findViewById(R.id.input_search);



        if(isServicesOK()){
            //init();

            Intent intent =new Intent(ProfileActivity.this,MapActivity.class);
            startActivityForResult(intent,1);

            input_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(ProfileActivity.this,MapActivity.class);
                    startActivityForResult(intent,2);
                }
            });
        }

        Recommandation recommandation =new Recommandation(getApplicationContext());
        int i=recommandation.getRecommandation();
        System.out.println(i);

        String[][] dataStrings =new String[35][3];
        AllData allData =new AllData(getApplicationContext());
        try {
            dataStrings=allData.getData("data.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onCreate: "+dataStrings[i][0]);
        //Toast.makeText(getApplicationContext(),dataStrings[i][0],Toast.LENGTH_LONG).show();

        final String[] recc =new String[3];

        recc[0]=""+dataStrings[i][0];
        recc[1]=""+dataStrings[i][1];
        recc[2]=""+dataStrings[i][2];




        gridView=findViewById(R.id.GridViewId);
        types = getResources().getStringArray(R.array.Types_name);

        CustomAdapter customAdapter;
        customAdapter = new CustomAdapter(this, types,flags);
        gridView.setAdapter(customAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value= types[position].trim();

                if(value.equalsIgnoreCase("Top picks".trim())){
                    Intent intent =new Intent(ProfileActivity.this,TopPicks.class);
                    startActivity(intent);

                }else if(value.equalsIgnoreCase("Restaurent".trim())){
                    Intent intent =new Intent(ProfileActivity.this,Restaurent.class);
                    startActivity(intent);

                }else if(value.equalsIgnoreCase("Sea-Breach".trim())){
                    Intent intent =new Intent(ProfileActivity.this,SeaBeach.class);
                    startActivity(intent);

                }else if(value.equalsIgnoreCase("park".trim())){
                    Intent intent =new Intent(ProfileActivity.this,Park.class);
                    startActivity(intent);

                }else if(value.equalsIgnoreCase("zoo".trim())){
                    Intent intent =new Intent(ProfileActivity.this,Zoo.class);
                    startActivity(intent);

                }else if(value.equalsIgnoreCase("cafe".trim())){
                    Intent intent =new Intent(ProfileActivity.this,Cafe.class);
                    startActivity(intent);

                }else if(value.equalsIgnoreCase("Cinema".trim())){
                    Intent intent =new Intent(ProfileActivity.this,Cinema.class);
                    startActivity(intent);

                }else if(value.equalsIgnoreCase("Museum".trim())){
                    Intent intent =new Intent(ProfileActivity.this,Museum.class);
                    startActivity(intent);

                }else if(value.equalsIgnoreCase("Hill".trim())){
                    Intent intent =new Intent(ProfileActivity.this,Hill.class);
                    startActivity(intent);

                }else if(value.equalsIgnoreCase("Waterfall".trim())){
                    Intent intent =new Intent(ProfileActivity.this,WaterFall.class);
                    startActivity(intent);

                }else if(value.equalsIgnoreCase("Resort".trim())){
                    Intent intent =new Intent(ProfileActivity.this,Resort.class);
                    startActivity(intent);

                }else if(value.equalsIgnoreCase("Recommandation".trim())){
                    Intent intent =new Intent(ProfileActivity.this,Suggest_place.class);
                    intent.putExtra("key",recc);
                    startActivity(intent);
                }


                //Toast.makeText(ProfileActivity.this,value,Toast.LENGTH_LONG).show();

            }
        });

    }
//    private void init(){
//        Button btnMap = (Button) findViewById(R.id.btnMap);
//        btnMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ProfileActivity.this, MapActivity.class);
//                startActivity(intent);
//            }
//        });
//    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ProfileActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(ProfileActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }



    private void logout(){

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.logout_menu){
            logout();
        }else if(item.getItemId()== R.id.profile){
            Intent intent =new Intent(ProfileActivity.this,Profile.class);
            startActivity(intent);

        }else if(item.getItemId()== R.id.history){
            Intent intent =new Intent(ProfileActivity.this,History.class);
            startActivity(intent);
        }else if(item.getItemId()== R.id.indebtedTo){
            Intent intent =new Intent(ProfileActivity.this,IndebtedTo.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode==1){
            device_location.setText(data.getStringExtra("key"));

        }else if(resultCode==2){
            input_search.setText(data.getStringExtra("key"));
        }
    }
}
