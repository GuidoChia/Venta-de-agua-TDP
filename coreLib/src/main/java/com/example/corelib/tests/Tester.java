package com.example.corelib.tests;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import customer.Customer;
import exceptions.WorkbookException;
import infos.BuyInfo;
import infos.ConcreteBuyInfo;
import infos.ConcretePriceInfo;
import infos.OutputInfo;
import infos.PriceInfo;
import reader.ConcreteCustomerManager;
import reader.ConcreteReader;
import reader.CustomerManager;
import reader.ExcelReader;
import writer.ConcreteWriter;
import writer.ExcelWriter;

class Tester {
    public static void main(String[] args) {
        /*
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.get(Calendar.MONTH));
        */

        /*
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        String baseDir = "/Ypora Clientes/";
        String name = "Abdala Agustina";

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

        writer.WriteBuy(info, prices, res);*/

        /*
        ExcelReader reader = ConcreteReader.getInstance();
        OutputInfo out = null;

        try {
            out = reader.readInfo(res);
        } catch (WorkbookException e) {
            e.printStackTrace();
        }

        System.out.print(res.getName() + " ");
        System.out.print("Saldo: " + out.getBalance() + " ");
        System.out.print("debe de 20: " + out.getTwentyBalance() + " ");
        System.out.print("debe de 12: " + out.getTwelveBalance() + " ");
        System.out.print("Date: " + out.getLastDate() + " ");
        System.out.println("Debe total: " + out.getCanistersBalance());
        */

        /*
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
        */

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


        ExcelReader reader = ConcreteReader.getInstance();

        File path = new File("");

        Date today = Calendar.getInstance().getTime();
        Date monthBefore = getMonthBefore(today);

        Date[] months = new Date[]{today, monthBefore};
        Collection<Customer> customers = reader.readCostumersMonth(months, path);

        CustomerManager manager = new ConcreteCustomerManager(customers);

        System.out.println("Resultado manager paid: " + manager.getPaid());
        System.out.println("Resultado manager 12: " + manager.getTwelveBought());
        System.out.println("Resultado manager 20: " + manager.getTwentyBought());

        Collection<Customer> customerCollection = manager.getRoute();

        File father = new File(path, "Ypora Recorridos");
        father.mkdirs();

        File routeFile = new File(father, "RecorridoToday.xls");
        try {
            routeFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExcelWriter writer = ConcreteWriter.getInstance();

        writer.WriteRoute(customerCollection, routeFile);
    }

    private static Date getMonthBefore(Date date) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = format.format(date);
        String[] strings = dateString.split("/");
        String resString;
        if (Integer.parseInt(strings[1]) == 1) {
            strings[1] = "12";
            int year = Integer.parseInt(strings[2]) - 1;
            strings[2] = String.valueOf(year);
        } else {
            int month = Integer.parseInt(strings[1]) - 1;
            strings[1] = String.valueOf(month);
        }

        resString = strings[0] + '/' + strings[1] + '/' + strings[2];

        try {
            return format.parse(resString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }
}

