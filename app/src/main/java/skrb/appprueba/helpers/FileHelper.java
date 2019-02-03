package skrb.appprueba.helpers;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Abstract class FileHelper represents a helper for the files objects.
 *
 * @author Guido Chia
 */
public abstract class FileHelper {
    private static String baseDir = "Ypora Clientes";

    /*
    This is like this so it can be changed to work on the SD card and not the external storage
     */
    private static File path = Environment.getExternalStorageDirectory();

    /**
     * Finds the file of the Customer, and returns it. If it can't find it, it creates one.
     *
     * @param name Name of the customer
     * @return The excel file of the customer.
     */
    public static File findFileWrite(String name) {

        File directoryParent = new File(path, baseDir);
        File directory = new File(directoryParent, String.valueOf(name.charAt(0)));
        directory.mkdirs();

        File res = new File(directory, name + ".xlsx");

        if (!res.exists()) {
            res = new File(directory, name + ".xls");
        }

        try {
            res.createNewFile();
        } catch (IOException e) {
            Log.e("IO error ", e.getClass().toString(), e);
        }

        return res;
    }

    /**
     * Finds the file from the customer named 'name'
     *
     * @param name Name of the customer
     * @return a File object representing the customer's file.
     */
    public static File findFileRead(String name) {
        File directoryParent = new File(path, baseDir);
        File directory = new File(directoryParent, String.valueOf(name.charAt(0)));
        directory.mkdirs();
        File res = new File(directory, name + ".xls");

        if (!res.exists()) {
            res = new File(directory, name + ".xlsx");
        }

        return res;
    }

    /**
     * Gets all the names from the customer's folder.
     *
     * @return an array with the names of the customers.
     */
    public static String[] initClientes() {
        List<String> strings = new LinkedList<>();
        File directory = new File(path, "Ypora Clientes");
        directory.mkdirs();


        for (File f : directory.listFiles()) {
            if (f.isDirectory()) {
                for (File finalFile : f.listFiles()) {
                    String fileName = finalFile.getName();
                    if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
                        String[] split = fileName.split(Pattern.quote("."));
                        strings.add(split[0]);
                    }
                }
            }
        }

        String[] res = strings.toArray(new String[0]);
        Arrays.sort(res);

        return res;

    }

    /**
     * Creates the file for the route
     *
     * @return a File object representing the file
     */
    public static File createFileRoute() {
        File folder = new File(path, "Ypora Recorridos");
        folder.mkdirs();
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String date = day + "_" + month + "_" + year;
        String fileName = "Recorrido_dia_" + date + ".xls";
        File file = new File(folder, fileName);


        try {
            file.createNewFile();
        } catch (IOException e) {
            Log.e("File not created", e.getClass().toString(), e);
        }

        return file;
    }

    /**
     * Gets the current path (External primary root or External secondary root)
     *
     * @return the current path
     */
    public static File getPath() {
        return path;
    }


}
