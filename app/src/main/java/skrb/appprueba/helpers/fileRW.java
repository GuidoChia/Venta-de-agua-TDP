package skrb.appprueba.helpers;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;


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
        String[] strings = name.split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            strings[i] = Character.toUpperCase(strings[i].charAt(0)) + strings[i].substring(1);
            if (i != strings.length - 1)
                builder.append( strings[i] + " ");
            else
                builder.append(strings[i]);
        }

        String finalName = builder.toString();
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

    static String[] initClientes() {
        List<String> strings = new LinkedList<>();
        File path = Environment.getExternalStorageDirectory();
        File directory = new File(path, "Ypora Clientes");
        directory.mkdirs();

        for (File f : directory.listFiles()) {
            if (f.isDirectory()) {
                for (File finalFile : f.listFiles()) {
                    String fileName = finalFile.getName();
                    if (fileName.endsWith(".xls")) {
                        String[] split = fileName.split(Pattern.quote("."));
                        strings.add(split[0]);
                    }
                }
            }
        }

        return strings.toArray(new String[0]);

    }


}
