package com.example.corelib.tests;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class testerParameters {

    public static void main (String [] args) {
       String dateString = "1/1/16";
       DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date=null;
        try {
           date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date today = Calendar.getInstance().getTime();
        System.out.println(today.toString());
        System.out.println(date.toString());
    }
}
