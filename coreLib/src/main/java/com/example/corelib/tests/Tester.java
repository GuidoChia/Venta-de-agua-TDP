package com.example.corelib.tests;



import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;

import infos.BuyInfo;
import infos.ConcreteBuyInfo;
import infos.ConcretePriceInfo;
import infos.OutputInfo;
import infos.PriceInfo;
import reader.ConcreteReader;
import reader.ExcelReader;
import writer.ConcreteWriter;
import writer.ExcelWriter;

public class Tester {
    public static void main(String [] args){
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();

        String name = "Ancin Nidia";

        BuyInfo info = new ConcreteBuyInfo(100, 2, 0, 0, 0, today, name);
        PriceInfo prices = new ConcretePriceInfo(50, 70);

        ExcelWriter writer = new ConcreteWriter();

        writer.WriteBuy(info, prices);

        ExcelReader reader = new ConcreteReader();
        OutputInfo out = null;
        try {
            out= reader.readInfo(name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Saldo: "+out.getBalance());
        System.out.println("debe de 20: "+out.getTwentyBalance());
        System.out.println("debe de 12: "+out.getTwelveBalance());
        System.out.println("Date: "+out.getLastDate());
        System.out.println("Debe total: "+out.getCanistersBalance());

    }
}

