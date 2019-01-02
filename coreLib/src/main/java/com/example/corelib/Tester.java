package com.example.corelib;

import java.util.Calendar;
import java.util.Date;

public class Tester {
    public static void main(String [] args){
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        BuyInfo info = new ConcreteBuyInfo(100, 2, 0, 2, today, "Perez Juan");

        PriceInfo prices = new ConcretePriceInfo(50, 70);

        ExcelWriter writer = new ConcreteWriter();

        writer.WriteBuy(info, prices);
    }
}

