package CHFileProcesser;

import au.com.bytecode.opencsv.CSVReader;

import java.io.IOException;

public class Utils {
    public static int getColumnQuantity(CSVReader csvreader) throws IOException {
        return csvreader.readNext().length;
    }

    public static String[] getHeader(CSVReader csvReader, boolean header) throws IOException {
        if (header) return csvReader.readNext();

        int columnQuantity = getColumnQuantity(csvReader);

        String[] resultHeader = new String[columnQuantity];
        for (int i=0; i<columnQuantity; i++) resultHeader[i] = "a" + Integer.toString(i+1);
        return resultHeader;
    }
}
