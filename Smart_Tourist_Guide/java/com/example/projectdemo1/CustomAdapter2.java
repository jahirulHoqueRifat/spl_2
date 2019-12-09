package com.example.projectdemo1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class CustomAdapter2 extends BaseAdapter {

    Context context;
    int flags;
    String[] name;
    String[] address;
    String[] rating;

    public CustomAdapter2(Context context, int flags, String[] place, String[] address, String[] rating) {
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
        return address.length;
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
        textView.setText(name[position]);
        textView2.setText(address[position]);
        textView3.setText(rating[position]);

        return convertView;
    }
}

