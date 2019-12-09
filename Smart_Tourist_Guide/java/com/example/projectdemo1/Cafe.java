package com.example.projectdemo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Cafe extends AppCompatActivity {

    ListView listView;

    int flag=R.drawable.museum;
    String name[]; String address[];
    String rating[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);

        name=getResources().getStringArray(R.array.cafe_name);
        address=getResources().getStringArray(R.array.cafe_address);
        rating=getResources().getStringArray(R.array.cafe_rating);

        listView=findViewById(R.id.listViewId);
        CustomAdapter2 adapter= new CustomAdapter2(this,flag,name,address, rating);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String value = name[position].toString();
                Intent intent =new Intent(getApplicationContext(),MapActivity.class);
                intent.putExtra("key",value);
                startActivity(intent);

            }
        });
    }
}
