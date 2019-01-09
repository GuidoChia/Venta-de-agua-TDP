package skrb.appprueba.helpers;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Interface fileRW represents a helper to find a workbook and write it to storage.
 * @author Guido Chia
 */
public interface fileRW {
    String baseDir ="Ypora Clientes/";

    /**
     * Finds the file of the Customer, and returns it. If it can't find it, it creates one.
     * @param name Name of the customer
     * @return The excel file of the customer.
     */
    static File findFile(String name){
        File path = Environment.getExternalStorageDirectory();
        String[] strings = name.split(" ");
        String finalName = "";
        for (int i=0; i< strings.length; i++){
            strings[i]=Character.toUpperCase(strings[i].charAt(0))+strings[i].substring(1);
            if (i!= strings.length-1)
                finalName+=strings[i]+" ";
            else
                finalName+=strings[i];
        }

        File directory = new File(path,baseDir+finalName.charAt(0));
        directory.mkdirs();

        File res = new File(directory,finalName+".xls");

        try {
            res.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;

    }



}
