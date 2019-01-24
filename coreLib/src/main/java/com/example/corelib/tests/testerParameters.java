package com.example.corelib.tests;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import customer.Customer;
import reader.ConcreteCustomerManager;
import reader.ConcreteReader;
import reader.CustomerManager;
import reader.ExcelReader;

class testerParameters {

    public static void main(String[] args) {
        ExcelReader reader = ConcreteReader.getInstance();

        File path = new File("");

        Date today = Calendar.getInstance().getTime();

        Date[] days = new Date[]{today};
        Collection<Customer> customers = reader.readCostumersDays(days, path);

        for (Customer c: customers){
            System.out.print(c.getName()+" ");
            System.out.println("Pagado: "+ c.getPaid());
        }


        System.out.println();
        CustomerManager manager = new ConcreteCustomerManager(customers);

        System.out.println("Resultado manager paid: " + manager.getPaid());
        System.out.println("Resultado manager 12: " + manager.getTwelveBought());
        System.out.println("Resultado manager 20: " + manager.getTwentyBought());


    }
}
