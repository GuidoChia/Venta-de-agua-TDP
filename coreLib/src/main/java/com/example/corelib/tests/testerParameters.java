package com.example.corelib.tests;

import java.util.Calendar;

import infos.ConcreteOutputInfo;
import infos.OutputInfo;

public class testerParameters {

    public static void main (String [] args) {
        String name = "guido chia";
        String[] strings = name.split(" ");
        String finalName = "";
        for (int i=0; i< strings.length; i++){
            strings[i]=Character.toUpperCase(strings[i].charAt(0))+strings[i].substring(1);
            if (i!= strings.length-1)
                finalName+=strings[i]+" ";
            else
                finalName+=strings[i];
        }

        System.out.println("Nombre antes: "+name+"p");
        System.out.println("Nombre despues: "+finalName+"p");
    }
}
