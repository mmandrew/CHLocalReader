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
        return String.format("SELECT parseDateTimeBestEffort(\\\"%s\\\") FROM _local.table", columnName);
    }

    private String createCommand(String columnName) {
        String resultCommand = this.createSedPart() + this.fileAddress + " | clickhouse-local --structure ";
        resultCommand += "\"" + this.createTableStructure() + "\"";
        resultCommand += " --input-format \"CSV\"";
        resultCommand += " --query \"" + this.createQuery(columnName) + "\"" + " 1>/dev/null";

        return resultCommand;
    }

    public boolean checkColumnIfDate(String columnName) throws IOException, InterruptedException {
        String checkingCommand = this.createCommand(columnName);
        System.out.println(checkingCommand);
        ProcessBuilder processBuilder = new ProcessBuilder();
        //TODO: adapt processBuilder for other os
        processBuilder.command("bash", "-c", checkingCommand);
        Process process = processBuilder.start();
        //StringBuilder output = new StringBuilder();
        //BufferedReader reader = new BufferedReader(
        //        new InputStreamReader(process.getInputStream()));
        //String line;
        //while ((line = reader.readLine()) != null) {
        //    output.append(line).append("\n");
        //}
        int exitVal = process.waitFor();
        if (exitVal != 0) return false;

        return true;
    }

    public void checkColumnIfFloat() {

    }
}
