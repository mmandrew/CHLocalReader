package CHFileProcesser;

import au.com.bytecode.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ColumnTypeDefiner {
    private String fileAddress;
    private CSVReader csvReader;
    private String[] header;
    private boolean isHeader;
    private int headerLength;

    public ColumnTypeDefiner(String fileAddress, CSVReader csvReader, String[] header, boolean isHeader) {
        this.isHeader = isHeader;
        this.fileAddress = fileAddress;
        this.csvReader = csvReader;
        this.header = header;
        this.headerLength = header.length;
    }

    private String createSedPart() {
        if (this.isHeader) return "sed -n '2,$p' ";
        return "sed -n '1,$p' ";
    }


    private String createTableStructure() {
        StringBuilder structure = new StringBuilder();
        structure.append("\\\"").append(this.header[0]).append("\\\"").append(" String");
        for (int i=1; i<this.headerLength; i++) {
            structure.append(", ").append("\\\"").append(this.header[i]).append("\\\"").append(" String");
        }
        return structure.toString();
    }

    private String createQuery(String columnName) {
        String query = String.format(
                "SELECT sum(case when parseDateTime64BestEffortOrNull(\\\"%s\\\") is not null and \\\"%s\\\" is not null then 0"+
                " when parseDateTime64BestEffortOrNull(\\\"%s\\\") is null and (\\\"%s\\\" is null or \\\"%s\\\"='') then 0 else 1 end) as val" +
                " FROM _local.table", columnName, columnName, columnName, columnName, columnName);
        return query;
    }

    private String createCommand(String columnName) {
        String resultCommand = this.createSedPart() + this.fileAddress + " | clickhouse-local --structure ";
        resultCommand += "\"" + this.createTableStructure() + "\"";
        resultCommand += " --input-format \"CSV\"";
        resultCommand += " --query \"" + this.createQuery(columnName) + "\"";

        return resultCommand;
    }

    public void checkColumnIfDate(String columnName) throws IOException, InterruptedException {
        String checkingCommand = this.createCommand(columnName);
        System.out.println(checkingCommand);
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", checkingCommand);
        Process process = processBuilder.start();
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        String line;
        System.out.println("output");
        while ((line = reader.readLine()) != null) {
            output.append(line + "\n");
        }

        int exitVal = process.waitFor();

        System.out.println(exitVal);
        System.out.println(output);
    }
}
