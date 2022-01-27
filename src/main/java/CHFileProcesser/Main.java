package CHFileProcesser;

import au.com.bytecode.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        //String fileAddress = "/home/andrei/Files/sales_million_records.csv";
        String fileAddress = "/home/andrei/Files/5m_sales.csv";
        //String fileAddress = "/home/andrei/Files/cut.csv";
        FileConnector fileConnector = new FileConnector(fileAddress);
        CSVReader csvReader = fileConnector.makeFileReader(',', '"', true);


        ColumnTypeDefiner columnTypeDefiner = new ColumnTypeDefiner(
                fileAddress,
                csvReader,
                fileConnector.getHeader(),
                fileConnector.isHeader()
        );
        System.out.println(columnTypeDefiner.checkColumnIfDate("Order Date"));
    }
}
