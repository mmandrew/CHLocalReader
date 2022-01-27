package CHFileProcesser;


import au.com.bytecode.opencsv.CSVReader;
import lombok.Getter;
import lombok.Setter;

import java.io.FileReader;
import java.io.IOException;


@Getter
@Setter
public class FileConnector {
    private String fileAddress;
    private String[] header;
    private int columnQuantity;

    public FileConnector(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public CSVReader makeFileReader(char delimiter, char quote, boolean header) throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(this.fileAddress), delimiter, quote);
        this.header = Utils.getHeader(csvReader, header);
        this.columnQuantity = Utils.getColumnQuantity(csvReader);
        return csvReader;
    }

    public String[] getHeader() {return this.header;}

}
