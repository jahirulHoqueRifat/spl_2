package com.example.projectdemo1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter3 extends BaseAdapter {

    Context context;
    int flags;
    String name;
    String address;
    String rating;

    public CustomAdapter3(Context context, int flags, String place, String address, String rating) {
        this.context = context;
        this.flags = flags;
        name = place;
        this.address = address;
        this.rating = rating;
    }

    private LayoutInflater inflater;


    @Override
    public int getCount()
    {
        return 1;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView==null){
            inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.secondfile,parent,false);

        }

        ImageView imageView=  convertView.findViewById(R.id.imageViewId);
        TextView textView= convertView.findViewById(R.id.nameId);
        TextView textView2= convertView.findViewById(R.id.addressId);
        TextView textView3= convertView.findViewById(R.id.rating);

        imageView.setImageResource(flags);
        textView.setText(name);
        textView2.setText(address);
        textView3.setText(rating);

        return convertView;
    }
}
