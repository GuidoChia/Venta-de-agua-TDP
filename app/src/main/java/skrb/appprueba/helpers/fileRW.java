package skrb.appprueba.helpers;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Interface fileRW represents a helper to find a workbook and write it to storage.
 *
 * @author Guido Chia
 */
public abstract class fileRW {
    private static String baseDir = "Ypora Clientes";
    private static File path = Environment.getExternalStorageDirectory();

    /**
     * Finds the file of the Customer, and returns it. If it can't find it, it creates one.
     *
     * @param name Name of the customer
     * @return The excel file of the customer.
     */
    public static File findFileWrite(String name) {
        String[] strings = name.split(" ");
        StringBuilder builder = new StringBuilder(25);
        for (int i = 0; i < strings.length; i++) {
            strings[i] = Character.toUpperCase(strings[i].charAt(0)) + strings[i].substring(1);
            if (i != (strings.length - 1))
                builder.append(strings[i])
                        .append(' ');
            else
                builder.append(strings[i]);
        }

        String finalName = builder.toString();
        File directoryParent = new File(path, baseDir);
        File directory = new File(directoryParent, String.valueOf(finalName.charAt(0)));
        directory.mkdirs();

        File res = new File(directory, finalName + ".xls");

        try {
            res.createNewFile();
        } catch (IOException e) {
            Log.e("IO error ", e.getClass().toString(), e);
        }

        return res;
    }

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

        return strings.toArray(new String[0]);

    }

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

    public static File getPath() {
        return path;
    }


}
