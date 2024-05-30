import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class CSVFormatter
{
    public static void main ( String[] args ) {
        CSVFormatter file = new CSVFormatter ();
        file.csvFormat ();
    }

    private void csvFormat ( ) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Insert the name of the csv: ");
            String fileName = input.nextLine();
            String sourceFilePath = "C:\\Users\\diogo\\Documents\\GitHub\\SAD_PBI_2\\implementacao\\Fase2\\Output\\csv\\TASKDATA" + fileName + ".csv";
            String destinationFilePath = "C:\\Users\\diogo\\Documents\\GitHub\\SAD_PBI_2\\implementacao\\Fase2\\Output\\csv\\TASKDATA" + fileName + ".csv";
            Path pathInput = Paths.get ( sourceFilePath );
            Path pathOutput = Paths.get ( destinationFilePath );
            System.out.print("Insert the name of the aggregator column: ");
            String aggregator = input.nextLine();
            System.out.print("Insert the name of the aggregate column: ");
            String aggregate = input.nextLine();
            try (
                    final BufferedReader reader = Files.newBufferedReader(pathInput,StandardCharsets.UTF_8);
                    final CSVPrinter printer = CSVFormat.RFC4180.withHeader(aggregate).withQuote(null).print(pathOutput , StandardCharsets.UTF_8);
            )
            {
                Iterable < CSVRecord > records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse (reader);
                for (CSVRecord record : records) {
                    String aggregatorValue = record.get ( aggregator );
                    String aggregateValue = record.get ( aggregate );
                    aggregateValue = aggregateValue.replaceAll("\\s", "");
                    aggregateValue = aggregateValue.replaceAll("[\"“”‘’']", ""); 
                    System.out.println ( aggregator + ": " + aggregatorValue + " | " +  aggregate + ": " + aggregateValue );
                    printer.printRecord ( aggregateValue );
                }
            }
        } catch ( InvalidPathException e ){
            e.printStackTrace ();
        } catch ( IOException e ){
            e.printStackTrace ();
        }
    }
}