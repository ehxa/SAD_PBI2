import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import java.io.*;
import java.util.Scanner;

public class CSVToARFFConverter {
    public static void convertCSVToARFF() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Insert the name of the csv: ");
        String fileName = input.nextLine();
        CSVLoader loader = new CSVLoader();
        String sourceFilePath = "../Output/csv/" + fileName + ".csv";
        String destinationFilePath = "../Output/arff/" + fileName + ".arff";
        loader.setSource(new File(sourceFilePath));
        Instances data = loader.getDataSet();
        System.out.println(data.get(0));

        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(new File(destinationFilePath));
        saver.writeBatch();
    }

    public static void main(String[] args) {
        try {
            convertCSVToARFF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
