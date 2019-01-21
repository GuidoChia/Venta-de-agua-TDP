package com.example.corelib.tests;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import customer.Customer;
import exceptions.WorkbookException;
import infos.OutputInfo;
import reader.ConcreteReader;
import reader.ExcelReader;

class testerParameters {

    public static void main(String[] args) {
        ExcelReader reader = ConcreteReader.getInstance();
        OutputInfo out = null;
        int i = 0;
        int noLeido = 0;
        File path = new File("");
        String dateString = "07/01/2018";
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date[] days = new Date[]{date};

        Collection<Customer> customers = reader.readCostumersDays(days,path);

        for (Customer c: customers) {
            System.out.println(c.getName());
        }
        /*for (File directory : path.listFiles()) {
            if (directory.isDirectory()) {
                for (File file : directory.listFiles()) {
                    if (file.isFile()) {
                        try {
                            out = reader.readInfo(file);
                        } catch (WorkbookException e) {
                            e.printStackTrace();
                        }

                       /* String secondLimit = "01/05/2018";
                        String firstLimit = "01/01/2018";
                        Date firstLimitDate = null;
                        Date secondLimitDate = null;
                        try {
                            firstLimitDate = new SimpleDateFormat("dd/MM/yyyy").parse(firstLimit);
                            secondLimitDate = new SimpleDateFormat("dd/MM/yyyy").parse(secondLimit);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (out != null) {
                            if (out.getLastDate().before(secondLimitDate) && out.getLastDate().after(firstLimitDate)) {
                                System.out.print("nombre: " + file.getName() + ' ');
                                System.out.println("Date: " + out.getLastDate() + " ");
                            }

                            i++;
                        } else {
                            noLeido++;
                        }
                    }
                }
            }
        } */
    }
}
