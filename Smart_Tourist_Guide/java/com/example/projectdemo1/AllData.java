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

class AllData {

    Context context;

    public AllData(Context myContext) {
        context=myContext;

    }

    public String[][] getData(String location) throws FileNotFoundException {

        String[][] dataStrings =new String[35][3];

        BufferedReader reader;

        try{
            final InputStream file =context.getAssets().open("data.txt");
            reader = new BufferedReader(new InputStreamReader(file));

            String line = reader.readLine();
            int i=0;
            while(line != null){
                Log.d("StackOverflow", line);


                int j=0;
                for (String retval: line.split(",")) {

                    dataStrings[i][j]= retval.trim();

                    //System.out.printf("datastring[%d][%d] =%s  ",i,j,dataStrings[i][j]);
                    j++;

                }
                i++;
                //System.out.println();
                line = reader.readLine();

            }
            file.close();
        } catch(IOException ioe){
            ioe.printStackTrace();
        }

//        //File file = new File(location);
//        try {
//            BufferedReader readFile = new BufferedReader(new FileReader(file));
//            String line;
//
//            int i=0;
//            while ((line = readFile.readLine()) != null) {
//
//
//                int j=0;
//                for (String retval: line.split(",")) {
//
//                    dataStrings[i][j]= retval;
//
//                    //System.out.printf("datastring[%d][%d] =%s  ",i,j,dataStrings[i][j]);
//                    j++;
//
//                }
//                //System.out.println();
//
//                i++;
//
//
//
//            }
//            readFile.close();
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
