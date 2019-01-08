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

/**
 * Interface fileRW represents a helper to find a workbook and write it to storage.
 * @author Guido Chia
 */
public interface fileRW {
    String baseDir ="/Ypora Clientes/";

    /**
     * Finds the workbook of the customer "name" and opens it.
     * If it can found it, returns null.
     * @param name Name of the customer
     * @return The workbook of the customer, or null if it can't find it.
     */
    static File findFile(String name){
        File path = Environment.getExternalStorageDirectory();

        File directory = new File(path,baseDir+name.charAt(0));
        directory.mkdirs();

        File res = new File(directory,name+".xls");

        try {
            res.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;

    }


}
