package com.example.corelib.tests;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import customer.Customer;
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


        Date[] days = new Date[]{Calendar.getInstance().getTime()};

        Collection<Customer> customers = reader.readCostumersDays(days,path);

        for (Customer c: customers) {
            System.out.print(c.getName()+' ');
            System.out.print("Comprado 12 "+c.getTwelveBought()+" ");
            System.out.print("Comprado 20 "+c.getTwentyBought()+" ");
            System.out.println(c.getPaid());
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
