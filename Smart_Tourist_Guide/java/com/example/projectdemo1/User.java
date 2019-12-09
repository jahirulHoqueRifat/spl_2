package com.example.projectdemo1;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class User {

    Context context;
    public User(Context mycontext){
        context=mycontext;
    }


    public int[] getData(String location){
        int[] dataStrings =new int[36];
        int count =0;

        for(int j=0;j<36;j++) {
            dataStrings[j]=0;
        }

        AllData allData =new AllData(context);
        String[][] dataString =new String[35][3];

        try {
            dataString=allData.getData("data.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader reader;

        try{
            final InputStream file =context.getAssets().open(location);
            reader = new BufferedReader(new InputStreamReader(file));

            String line = reader.readLine();
            int i=0;
            while(line != null){
                Log.d("StackOverflow", line);


                for (String retval: line.split(",")) {


                    for(int j=0;j<35;j++) {
                        if(dataString[j][0].equalsIgnoreCase(retval.trim())) {
                            dataStrings[j]= 1;
                            count++;
                            //System.out.printf("datastring[%d] = %d  %s\n ",j,dataStrings[j],dataString[j][0]);
                            break;
                        }
                    }


                }

                i++;
                line = reader.readLine();

            }
            file.close();
            dataStrings[35]=count*100/35;
        } catch(IOException ioe){
            ioe.printStackTrace();
        }

//        File file = new File(location);
//
//        try {
//            BufferedReader readFile = new BufferedReader(new FileReader(file));
//            String line;
//
//            int i=0;
//            while ((line = readFile.readLine()) != null) {
//
//
//
//                for (String retval: line.split(",")) {
//
//
//                    for(int j=0;j<35;j++) {
//                        if(dataString[j][0].equalsIgnoreCase(retval.trim())) {
//                            dataStrings[j]= 1;
//                            count++;
//                            //System.out.printf("datastring[%d] = %d  %s\n ",j,dataStrings[j],dataString[j][0]);
//                            break;
//                        }
//                    }
//
//
//                }
//
//                i++;
//            }
//            readFile.close();
//            dataStrings[35]=count*100/35;
//
//
//        } catch (NumberFormatException e ) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }

        return dataStrings;
    }
}
