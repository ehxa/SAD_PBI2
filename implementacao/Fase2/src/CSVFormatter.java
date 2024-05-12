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
    public static void main ( String[] args )
    {
        System.out.println ( "Hello World!" );
        CSVFormatter file = new CSVFormatter ();
        file.csvFormat ();
    }

    private void csvFormat ( )
    {
        try
        {
            // Read CSV file.
             Scanner input = new Scanner(System.in);
            System.out.print("Insert the name of the csv: ");
            String fileName = input.nextLine();
            String sourceFilePath = "../Output/csv/" + fileName + ".csv";
            String destinationFilePath = "../Output/csv/" + fileName + "New.csv";
            Path pathInput = Paths.get ( sourceFilePath );
            Path pathOutput = Paths.get ( destinationFilePath );
            try (
                    final BufferedReader reader = Files.newBufferedReader ( pathInput , StandardCharsets.UTF_8 ) ;
                    final CSVPrinter printer = CSVFormat.RFC4180.withHeader ( "PRODUCTNAME" ).print ( pathOutput , StandardCharsets.UTF_8 ) ;
            )
            {
                Iterable < CSVRecord > records = CSVFormat.RFC4180.withFirstRecordAsHeader ().parse ( reader );
                // We expect these headers: ORDERNUMBER,PRODUCTNAME
                for ( CSVRecord record : records )
                {
                    // Read.
                    Integer orderNumber = Integer.valueOf ( record.get ( "ORDERNUMBER" ) );
                    String productName = record.get ( "PRODUCTNAME" );
                    productName = productName.replaceAll("\\s", "");
                    System.out.println ( "ORDERNUMBER: " + orderNumber + " | PRODUCTNAME: " + productName );

                    // Write.
                    printer.printRecord ( productName );
                }
            }
        } catch ( InvalidPathException e )
        {
            e.printStackTrace ();
        } catch ( IOException e )
        {
            e.printStackTrace ();
        }
    }
}