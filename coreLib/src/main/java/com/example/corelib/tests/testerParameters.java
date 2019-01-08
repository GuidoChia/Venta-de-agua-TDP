package com.example.corelib.tests;

import java.util.Calendar;

import infos.ConcreteOutputInfo;
import infos.OutputInfo;

public class testerParameters {

    public static void main (String [] args){
        OutputInfo out = null;

        out = createOutput(out);

        System.out.println("Saldo: "+out.getBalance());
        System.out.println("debe de 20: "+out.getTwentyBalance());
        System.out.println("debe de 12: "+out.getTwelveBalance());
        System.out.println("Date: "+out.getLastDate());
        System.out.println("Debe total: "+out.getCanistersBalance());
    }

    public static OutputInfo createOutput(OutputInfo out){
        if (out==null){
            return new ConcreteOutputInfo(Calendar.getInstance().getTime(),50,2,2);
        }
        return out;
    }
}
