package com.example.corelib.tests;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import customer.Customer;
import infos.BuyInfo;
import infos.ConcreteBuyInfo;
import infos.ConcretePriceInfo;
import infos.PriceInfo;
import reader.ConcreteCustomerManager;
import reader.ConcreteReader;
import reader.CustomerManager;
import reader.ExcelReader;
import writer.ConcreteWriter;
import writer.ExcelWriter;

class testerParameters {

    public static void main(String[] args) {
        ExcelReader reader = ConcreteReader.getInstance();

        File path = new File("");

        Date today = Calendar.getInstance().getTime();

        Date[] months = new Date[]{today};
        Collection<Customer> customers = reader.readCostumersMonth(months, path);

        CustomerManager manager = new ConcreteCustomerManager(customers);

        System.out.println("Resultado manager paid: " + manager.getPaid());
        System.out.println("Resultado manager 12: " + manager.getTwelveBought());
        System.out.println("Resultado manager 20: " + manager.getTwentyBought());

        /*for (int i = 0; i < 5; i++) {
            Date today = Calendar.getInstance().getTime();
            ExcelWriter writer = ConcreteWriter.getInstance();
            BuyInfo buy;

            File path = new File("/Ypora Clientes/");
            for (File directory : path.listFiles()) {
                if (directory.isDirectory()) {
                    for (File file : directory.listFiles()) {
                        String name = file.getName().substring(0, file.getName().length() - 4);
                        BuyInfo info = new ConcreteBuyInfo(200, 2, 0, 0, 0, today, name);
                        PriceInfo prices = new ConcretePriceInfo(50, 70);

                        writer.WriteBuy(info, prices, file);
                    }
                }
            }
        }*/
    }
}
