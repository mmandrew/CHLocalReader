package CHFileProcesser;


import au.com.bytecode.opencsv.CSVReader;
import com.univocity.parsers.csv.CsvFormat;
import lombok.Getter;
import lombok.Setter;
import scala.Char;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


@Getter
@Setter
public class FileConnector {
    private String fileAddress;

    public FileConnector(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public CSVReader makeFileReader(char delimiter, char quote) throws FileNotFoundException {
        CSVReader csvReader = new CSVReader(new FileReader(this.fileAddress), delimiter, quote);
        return csvReader;
    }
}
