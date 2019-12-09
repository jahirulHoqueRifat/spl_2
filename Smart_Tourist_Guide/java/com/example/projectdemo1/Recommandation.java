package com.example.projectdemo1;

import android.content.Context;

class Recommandation {

    Context context;
    public Recommandation(Context mycontext){
        context=mycontext;
    }

    public int getRecommandation(){


        int[] dataStrings =new int[36];
        int[] dataStrings2 =new int[36];
        int[] dataStrings3 =new int[36];
        int[] dataStrings4 =new int[36];
        int[] dataStrings5 =new int[36];

        User user=new User(context);
        User user2=new User(context);
        User user3=new User(context);
        User user4=new User(context);
        User user5=new User(context);

        dataStrings=user.getData("user1.txt");
        dataStrings2=user2.getData("user2.txt");
        dataStrings3=user3.getData("user3.txt");
        dataStrings4=user4.getData("user4.txt");
        dataStrings5=user5.getData("user5.txt");

//        for (int i = 0; i < dataStrings3.length; i++) {
//            System.out.printf("datastring[%d] = %d \t ",i,dataStrings[i]);
//            System.out.printf("datastring[%d] = %d \t ",i,dataStrings2[i]);
//            System.out.printf("datastring[%d] = %d \t ",i,dataStrings3[i]);
//            System.out.printf("datastring[%d] = %d \t ",i,dataStrings4[i]);
//            System.out.printf("datastring[%d] = %d \n ",i,dataStrings5[i]);
//        }


        double[][] compare =new double[4][2];

        for (int i = 0; i < compare.length; i++) {
            compare[i][0]=0;
            compare[i][1]=0;
        }


        for (int i = 0; i < dataStrings3.length-1; i++) {

            if(dataStrings3[i]==0) {
                for (int j = 0; j < dataStrings.length-1; j++) {
                    if(dataStrings3[i]== dataStrings3[j]) continue;
                    compare[0][0] += (dataStrings3[j]-(dataStrings3[35]/100))* (dataStrings[j]-(dataStrings[35]/100));
                    compare[1][0] += (dataStrings3[j]-(dataStrings3[35]/100))* (dataStrings2[j]-(dataStrings2[35]/100));
                    compare[2][0] += (dataStrings3[j]-(dataStrings3[35]/100))* (dataStrings4[j]-(dataStrings4[35]/100));
                    compare[3][0] += (dataStrings3[j]-(dataStrings3[35]/100))* (dataStrings5[j]-(dataStrings5[35]/100));
                }

//				for (int j = 0; j < compare.length; j++) {
//					System.out.printf("index: %d %f \n",i,compare[j][0]);
//				}


                double temp=0,temp1=0,temp2=0,temp3=0,temp4=0;

                for (int k = 0; k < dataStrings.length; k++) {
                    if(dataStrings3[i]== dataStrings3[k]) continue;

                    temp+=dataStrings[k]-(dataStrings[35]/100)*dataStrings[k]-(dataStrings[35]/100);
                    temp1+=dataStrings2[k]-(dataStrings2[35]/100)*dataStrings2[k]-(dataStrings2[35]/100);
                    temp2+=dataStrings3[k]-(dataStrings3[35]/100)*dataStrings3[k]-(dataStrings3[35]/100);
                    temp3+=dataStrings4[k]-(dataStrings4[35]/100)*dataStrings4[k]-(dataStrings4[35]/100);
                    temp4+=dataStrings5[k]-(dataStrings5[35]/100)*dataStrings5[k]-(dataStrings5[35]/100);


                }

                compare[0][1]=Math.sqrt(temp)*Math.sqrt(temp2);
                compare[1][1]=Math.sqrt(temp1)*Math.sqrt(temp2);
                compare[2][1]=Math.sqrt(temp3)*Math.sqrt(temp2);
                compare[3][1]=Math.sqrt(temp4)*Math.sqrt(temp2);

//				for (int j = 0; j < compare.length; j++) {
//					System.out.printf("index: %d %f \n",i,compare[j][1]);
//				}

                for (int j = 0; j < compare.length; j++) {
                    compare[j][0]/=compare[j][1];
                    //System.out.printf("index: %d %f \n",i,compare[j][0]);
                }


                int index=getMax(compare);
                //System.out.println(index);
                //if(index>=2) index++;

                //System.out.println(compare[index-1][0]);

                if(index==0) {
                    if(dataStrings[i]==1) return i;
                }else if(index==1) {
                    if(dataStrings2[i]==1) return i;
                }else if(index==2) {
                    if(dataStrings4[i]==1) return i;
                }else if(index==3) {
                    if(dataStrings5[i]==1) return i;
                }
            }

        }

        return 0;

    }

    private int getMax (double[][] compareMax) {

        double max=0;int i;

        for ( i = 0; i < compareMax.length; i++) {
            if(compareMax[i][0]>max) {
                max=compareMax[i][0];
            }
        }
        return i-1;
    }
}
