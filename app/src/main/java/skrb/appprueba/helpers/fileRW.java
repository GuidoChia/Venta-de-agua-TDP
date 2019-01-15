package skrb.appprueba.helpers;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

import static skrb.appprueba.helpers.AutoCompleteHelper.correctName;

/**
 * Interface fileRW represents a helper to find a workbook and write it to storage.
 *
 * @author Guido Chia
 */
public interface fileRW {
    String baseDir = "Ypora Clientes/";

    /**
     * Finds the file of the Customer, and returns it. If it can't find it, it creates one.
     *
     * @param name Name of the customer
     * @return The excel file of the customer.
     */
    static File findFileWrite(String name) {
        File path = Environment.getExternalStorageDirectory();

        String finalName = correctName(name);

        File directory = new File(path, baseDir + finalName.charAt(0));
        directory.mkdirs();

        File res = new File(directory, finalName + ".xls");

        try {
            res.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return res;
    }

    static File findFileRead(String name) {
        File path = Environment.getExternalStorageDirectory();
        File directory = new File(path, baseDir + name.charAt(0));
        directory.mkdirs();
        File res = new File(directory, name + ".xls");

        return res;
    }


}
