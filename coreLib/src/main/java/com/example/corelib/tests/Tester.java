package com.example.corelib.tests;


import java.io.File;
import java.io.IOException;
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
import reader.ConcreteMonthManager;
import reader.ConcreteReader;
import reader.ExcelReader;
import reader.MonthManager;
import writer.ConcreteWriter;
import writer.ExcelWriter;

public class Tester {
   public static void main(String [] args){
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        String baseDir ="/Ypora Clientes/";
        String name = "ancin nIdia";

        BuyInfo info = new ConcreteBuyInfo(100, 2, 0, 0, 0, today, name);
        PriceInfo prices = new ConcretePriceInfo(50, 70);

        ExcelWriter writer = ConcreteWriter.getInstance();

       File directory = new File(baseDir+name.charAt(0));
       directory.mkdirs();

       File res = new File(directory,name+".xls");

       System.out.println(res.getAbsolutePath());
       try {
           res.createNewFile();
       } catch (IOException e) {
           e.printStackTrace();
       }

        writer.WriteBuy(info, prices, res);

        ExcelReader reader = ConcreteReader.getInstance();
        OutputInfo out = null;
        try {
            out= reader.readInfo(res);
        } catch (WorkbookException e) {
            e.printStackTrace();
        }

        System.out.println("Saldo: "+out.getBalance());
        System.out.println("debe de 20: "+out.getTwentyBalance());
        System.out.println("debe de 12: "+out.getTwelveBalance());
        System.out.println("Date: "+out.getLastDate());
        System.out.println("Debe total: "+out.getCanistersBalance());

        int[] months ={1};
        int year = 2019;
        File path = new File("");

       Collection<Customer> customers = reader.readCostumers(months, year, path);

       MonthManager manager = new ConcreteMonthManager();
       System.out.println("Resultado manager: "+manager.getPaid(customers));

   }
}

