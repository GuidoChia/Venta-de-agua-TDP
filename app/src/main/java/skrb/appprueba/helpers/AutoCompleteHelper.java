package skrb.appprueba.helpers;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;


public interface AutoCompleteHelper {
    static String[] initClientes() {
        List<String> strings = new LinkedList<>();
        File path = Environment.getExternalStorageDirectory();
        File directory = new File(path, "Ypora Clientes");

        for (File f : directory.listFiles()) {
            if (f.isDirectory()) {
                for (File finalFile : f.listFiles()) {
                    String fileName = finalFile.getName();

                    if (fileName.endsWith(".xls")) {
                        String[] splitted = fileName.split(Pattern.quote("."));
                        String name = correctName (splitted[0]);
                        strings.add(name);
                    }
                }
            }
        }

        return strings.toArray(new String[0]);

    }
    static String correctName(String name){
        String[] strings = name.split(" ");
        String finalName = "";
        for (int i = 0; i < strings.length; i++) {
            strings[i] = Character.toUpperCase(strings[i].charAt(0)) + strings[i].substring(1);
            if (i != strings.length - 1)
                finalName += strings[i] + " ";
            else
                finalName += strings[i];
        }

        return finalName;
    }
}
