package com.example.corelib;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;

public class Tester {
    public static void main(String [] args){
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();

        BuyInfo info = new ConcreteBuyInfo(100, 2, 0, 0, 0, today, "Ancin Nidia");
        PriceInfo prices = new ConcretePriceInfo(50, 70);

        ExcelWriter writer = new ConcreteWriter();

        writer.WriteBuy(info, prices);

        ExcelReader reader = new ConcreteReader();
        OutputInfo out = null;
        try {
            out= reader.readInfo("Ancin Nidia");
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

