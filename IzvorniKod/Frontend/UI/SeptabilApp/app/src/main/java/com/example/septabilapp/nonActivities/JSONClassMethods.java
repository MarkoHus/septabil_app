package com.example.septabilapp.nonActivities;

import java.util.*;

public class JSONClassMethods {


    public static int num_of_JSON_Objects(String JSONString){
        char someChar = ',';
        int count = 0;

        for (int i = 0; i < JSONString.length(); i++) {
            if (JSONString.charAt(i) == someChar) {
                count++;
            }
        }

        count=count+1;
        return count;
    }


    //This method is recieving a JSONString and a number of pairs (Key:value), and returns a map.
    public static HashMap<String, String> JSONStringToMap(String JSONString, int len) {
        String str = new String();
        String straArr[] = new String[len];
        HashMap<String, String> map = new HashMap<String, String>();

        str=JSONString.trim();
        StringBuilder sb = new StringBuilder(str);
        sb.deleteCharAt(str.length() - 1);
//		sb.deleteCharAt(0);
        str = sb.toString();
        straArr=str.split(",");
        for (String pair : straArr){
            String straArr2[] = new String[len];
            String first=new String();
            String second=new String();
            straArr2=pair.split(":");
            first=straArr2[0];
            second=straArr2[1];

            StringBuilder Fsb = new StringBuilder(first);
            StringBuilder Ssb = new StringBuilder(second);

            Fsb.deleteCharAt(first.length() - 1);
            Fsb.deleteCharAt(0);
            Fsb.deleteCharAt(0);


            Ssb.deleteCharAt(second.length() - 1);
            Ssb.deleteCharAt(0);
            Ssb.deleteCharAt(0);


            first=Fsb.toString();
            second=Ssb.toString();

            map.put(first, second);
        }

        return map;
    }


    public static HashMap<String, String> JSONStringToMapJustJSON(String JSONString){
        int numOfJsonObj=num_of_JSON_Objects(JSONString);
        HashMap<String, String> mapa = JSONStringToMap(JSONString, numOfJsonObj);

        return mapa;
    }


}
