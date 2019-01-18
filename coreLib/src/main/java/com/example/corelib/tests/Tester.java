package com.example.corelib.tests;


import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import customer.Customer;
import exceptions.WorkbookException;
import infos.OutputInfo;
import reader.ConcreteCustomerManager;
import reader.ConcreteReader;
import reader.CustomerManager;
import reader.ExcelReader;

public class Tester {
    public static void main(String[] args) {

       /* Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        String baseDir = "/Ypora Clientes/";
        String name = "Perez Juan";

        BuyInfo info = new ConcreteBuyInfo(200, 2, 0, 0, 0, today, name);
        PriceInfo prices = new ConcretePriceInfo(50, 70);

        ExcelWriter writer = ConcreteWriter.getInstance();

        File directory = new File(baseDir + name.charAt(0));
        directory.mkdirs();

        File res = new File(directory, name + ".xls");

        System.out.println(res.getAbsolutePath());
        try {
            res.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        writer.WriteBuy(info, prices, res);
        */
        ExcelReader reader = ConcreteReader.getInstance();
        OutputInfo out = null;
        int i = 0;
        int noLeido= 0;
        File path = new File("/Ypora Clientes/");
        for (File directory : path.listFiles()) {
            if (directory.isDirectory()) {
                for (File file : directory.listFiles()) {
                    try {
                        out = reader.readInfo(file);
                    } catch (WorkbookException e) {
                        e.printStackTrace();
                    }
                    if (out != null) {
                        System.out.print(file.getName() + " ");
                        System.out.print("Saldo: " + out.getBalance() + " ");
                        System.out.print("debe de 20: " + out.getTwentyBalance() + " ");
                        System.out.print("debe de 12: " + out.getTwelveBalance() + " ");
                        System.out.print("Date: " + out.getLastDate() + " ");
                        System.out.println("Debe total: " + out.getCanistersBalance());
                        i++;
                    }
                    else{
                        noLeido++;
                    }
                }
            }
        }

        System.out.println("Total leidos: "+i);
        System.out.println("Total no leidos: "+noLeido);

        /*
        try {
            out = reader.readInfo(res);
        } catch (WorkbookException e) {
            e.printStackTrace();
        }

        System.out.println("Saldo: " + out.getBalance());
        System.out.println("debe de 20: " + out.getTwentyBalance());
        System.out.println("debe de 12: " + out.getTwelveBalance());
        System.out.println("Date: " + out.getLastDate());
        System.out.println("Debe total: " + out.getCanistersBalance());
        */

        /*
        ExcelReader reader = ConcreteReader.getInstance();

        File path = new File("");

        String dateString1 = "01/1/2019";
        //String dateString2 = "01/12/2018";

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date[] months = null;
        try {
            months = new Date[]{format.parse(dateString1)};
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Collection<Customer> customers = reader.readCostumersMonth(months, path);

        CustomerManager manager = new ConcreteCustomerManager(customers);

        System.out.println("Resultado manager paid: " + manager.getPaid());
        System.out.println("Resultado manager 12: " + manager.getTwelveBought());
        System.out.println("Resultado manager 20: " + manager.getTwentyBought());

        Collection<Customer> customerCollection = manager.getRoute();

        for (Customer c : customerCollection) {
            System.out.println("Name:" + c.getName());
        }*/
    }
}

