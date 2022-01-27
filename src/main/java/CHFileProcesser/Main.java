package CHFileProcesser;

import au.com.bytecode.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    /*public static void main(String[] args) throws IOException, InterruptedException {
        FileConnector fileConnector = new FileConnector("/home/andrei/Files/sales_million_records.csv");
        fileConnector.makeFileReader(',', '"', true);
        //System.out.println(Arrays.toString(fileConnector.getHeader()));

        //String cmd = "cat /home/andrei/Files/sales_million_records.csv | clickhouse-local --structure \"a1 String, a2 String, a3 String, a4 String, a5 String, a6 String, a7 String, a8 String, a9 String, a10 String, a11 String, a12 String, a13 String, a14 String\" " +
        //        "    --input-format \"CSV\" --query \"SELECT parseDateTime32BestEffortOrNull(a5) FROM _local.table\" 1>/dev/null";
        String cmd = "cat ~/Files/sales_million_records.csv | clickhouse-local --structure \"a1 String, a2 String, a3 String, a4 String, a5 String, a6 String, a7 String, a8 String, a9 String, a10 String, a11 String, a12 String, a13 String, a14 String\" \\\n" +
                "    --input-format \"CSV\" --query \"SELECT toInt32(a6) FROM _local.table\" 1>/dev/null";
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", cmd);
        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        String line;
        System.out.println("output");
        while ((line = reader.readLine()) != null) {
            output.append(line + "\n");
        }

        StringBuilder errOutput = new StringBuilder();
        BufferedReader errReader = new BufferedReader(
                new InputStreamReader(process.getErrorStream()));

        System.out.println("errors");
        while ((line = errReader.readLine()) != null) {
            errOutput.append(line + "\n");
        }

        int exitVal = process.waitFor();

        System.out.println(exitVal);
        System.out.println(output);
        System.out.println(errOutput);
    }*/
    public static void main(String[] args) throws IOException, InterruptedException {
        String fileAddress = "/home/andrei/Files/sales_million_records.csv";
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
