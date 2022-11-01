package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Tools {

    // Given an array list of string arrays, overwrites file with csv structure
    public static void overwriteCsv(String filePath, ArrayList<String[]> rows, String[] headers) throws IOException {
        var writer = new FileWriter(filePath);
        writer.write(String.join(",", headers));
        for (String[] row : rows){
            writer.write('\n' + String.join(",",row));
        }
        writer.close();
    }

    public static int getIndex(String[] array, String value){
        for (int i = 0; i < array.length; i++) {
            if (value.equals(array[i])) return i;
        }
        return -1;
    }
}
