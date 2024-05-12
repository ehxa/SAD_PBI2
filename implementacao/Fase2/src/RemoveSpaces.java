import java.io.*;
import java.util.Scanner;

public class RemoveSpaces{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.print("Insert the name of the csv: ");
        String fileName = input.nextLine();
        input.close();
        String sourceFilePath = "../Output/csv/" + fileName + ".csv";
        String destinationFilePath = "../Output/csv/" + fileName + "New.csv";
        File source = new File(sourceFilePath);
        try {
            Scanner csvScanner = new Scanner(source);
            PrintWriter csvWritter = new PrintWriter(destinationFilePath);
            while (csvScanner.hasNextLine()){
                String line = csvScanner.nextLine();
                String formatedLine = line.replace(" ", "").replace("\"","");
                csvWritter.println(formatedLine);
                System.out.println("Read line: " + line);
                System.out.println("New line: " + formatedLine);
            }
            csvScanner.close();
            csvWritter.close();
        }
        catch (FileNotFoundException e){
            System.out.println("Error opening file.");
        }
    }
}