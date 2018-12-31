package com.example.corelib;

import java.util.Calendar;
import java.util.Date;

public class Tester {
    public static void main(String [] args){
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        BuyInfo info = new ConcreteBuyInfo(50, 1, 1, 2, today, "Ancin Nidia");

        PriceInfo prices = new ConcretePriceInfo(25, 20);

        ExcelWriter writer = new ConcreteWriter();

        writer.WriteBuy(info, prices);
    }
}

