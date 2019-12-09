package com.example.projectdemo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Suggest_place extends AppCompatActivity {

    ListView listView;

    int flag=R.drawable.museum;
    String name; String address;
    String rating;
    String[] str=new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_place);


        Bundle bundle=getIntent().getExtras();
        if(bundle !=null){
            str=bundle.getStringArray("key");

        }

        name=""+str[0];
        address=""+str[1];
        rating=""+str[2];

        listView=findViewById(R.id.listViewId);
        CustomAdapter3 adapter= new CustomAdapter3(this,flag,name,address, rating);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String value = name.toString();
                Intent intent =new Intent(getApplicationContext(),MapActivity.class);
                intent.putExtra("key",value);
                startActivity(intent);

            }
        });

    }


}
