import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import java.io.*;

public class CSVToARFFConverter {
    public static void convertCSVToARFF() throws IOException {
        CSVLoader loader = new CSVLoader();
        String sourceFilePath = "Z:/Documents/GitHub/SAD_PBI2/Output/Japan.csv";
        String destinationFilePath = "Z:/Documents/GitHub/SAD_PBI2/Output/Japan.csv";
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
