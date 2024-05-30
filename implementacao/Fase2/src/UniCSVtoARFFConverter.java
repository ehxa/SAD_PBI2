import java.io.*;
import java.util.*;

public class UniCSVtoARFFConverter {
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Insert the name of the csv: ");
            String fileName = input.nextLine();
            String sourceFilePath = "C:\\Users\\diogo\\Documents\\GitHub\\SAD_PBI_2\\implementacao\\Fase2\\Output\\csv\\" + fileName + ".csv";
            String destinationFilePath = "C:\\Users\\diogo\\Documents\\GitHub\\SAD_PBI_2\\implementacao\\Fase2\\Output\\arff\\" + fileName + ".arff";
            System.out.println(sourceFilePath);
            File myObj = new File(sourceFilePath);
            Scanner myReader = new Scanner(myObj);
            try {
                FileWriter newFile = new FileWriter(destinationFilePath);
                BufferedWriter buffer = new BufferedWriter(newFile);
                String data = myReader.nextLine();
                buffer.write("@RELATION " + data);
                buffer.newLine();
                buffer.newLine();
                Set<String> hash_Set = new HashSet<String>(); 
                List<String[]> lines = new ArrayList<>();
                while (myReader.hasNextLine()) {
                    data = myReader.nextLine();
                    String[] products = data.split(",");
                    for (String product : products) {
                        hash_Set.add(product);
                    }
                    lines.add(products);
                }
                for (String product : hash_Set) {
                    buffer.write("@ATTRIBUTE " + product + " {y,?}");
                    buffer.newLine();
                }
                buffer.newLine();
                buffer.write("@DATA");
                buffer.newLine();
                for (String[] line : lines) {
                    List<String> thisLine = Arrays.asList(line);
                    for (String product : hash_Set) {
                        if (thisLine.contains(product)) {
                            buffer.write("y,");
                        } else {
                            buffer.write("?,");
                        }
                    }
                    buffer.newLine();
                }
                buffer.close();
            }
            catch (IOException e){
               System.out.println("Error with file.");
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}