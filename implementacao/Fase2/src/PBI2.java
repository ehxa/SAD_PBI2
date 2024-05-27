import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.derby.impl.store.raw.log.Scan;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.sdk.samples.embedding.GeneratingTransformations;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class PBI2 {
    public static void main(String[] args) throws IOException {
        PBI2 obj = new PBI2();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("SAD PBI 2 - Objetivo 3 ");
            System.out.println("1. csvFormat(); 2. convertCSVtoARFF(); 3. removeSpaces(); 4. uniCSVtoARFF(); 5.generatingTransformations()");
            System.out.print("Select function: ");
            switch (sc.nextInt()) {
                case 1:
                    obj.csvFormat();
                    break;
                case 2:
                    convertCSVToARFF();
                    break;
                case 3:
                    obj.removeSpaces();
                    break;
                case 4:
                    obj.uniCSVtoARFF();
                    break;
                case 5:
                    obj.generatingTransformations();
                    break;
                default:
                    break;
            }

        }
    }

    private void csvFormat() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Insert the name of the csv: ");
            String fileName = input.nextLine();
            String sourceFilePath = "C:\\Users\\diogo\\Documents\\GitHub\\SAD_PBI_2\\implementacao\\Fase2\\Output\\csv\\" + fileName + ".csv";
            String destinationFilePath = "C:\\Users\\diogo\\Documents\\GitHub\\SAD_PBI_2\\implementacao\\Fase2\\Output\\csv\\" + fileName + "New.csv";
            Path pathInput = Paths.get(sourceFilePath);
            Path pathOutput = Paths.get(destinationFilePath);
            System.out.print("Insert the name of the aggregator column: ");
            String aggregator = input.nextLine();
            System.out.print("Insert the name of the aggregate column: ");
            String aggregate = input.nextLine();
            try (
                    final BufferedReader reader = Files.newBufferedReader(pathInput, StandardCharsets.UTF_8);
                    final CSVPrinter printer = CSVFormat.RFC4180.withHeader(aggregate).withQuote(null).print(pathOutput, StandardCharsets.UTF_8);
            ) {
                Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
                for (CSVRecord record : records) {
                    String aggregatorValue = record.get(aggregator);
                    String aggregateValue = record.get(aggregate);
                    aggregateValue = aggregateValue.replaceAll("\\s", "");
                    aggregateValue = aggregateValue.replaceAll("[\"“”‘’']", "");
                    System.out.println(aggregator + ": " + aggregatorValue + " | " + aggregate + ": " + aggregateValue);
                    printer.printRecord(aggregateValue);
                }
            }
        } catch (InvalidPathException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void convertCSVToARFF() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Insert the name of the csv: ");
        String fileName = input.nextLine();
        CSVLoader loader = new CSVLoader();
        String sourceFilePath = "C:\\Users\\diogo\\Documents\\GitHub\\SAD_PBI_2\\implementacao\\Fase2\\Output\\csv\\" + fileName + ".csv";
        String destinationFilePath = "C:\\Users\\diogo\\Documents\\GitHub\\SAD_PBI_2\\implementacao\\Fase2\\Output\\csv\\" + fileName + ".arff";
        loader.setSource(new File(sourceFilePath));
        Instances data = loader.getDataSet();
        System.out.println(data.get(0));
        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(new File(destinationFilePath));
        saver.writeBatch();
    }

    private void removeSpaces() throws IOException{
        Scanner input = new Scanner(System.in);
        System.out.print("Insert the name of the csv: ");
        String fileName = input.nextLine();
        input.close();
        String sourceFilePath = "C:\\Users\\diogo\\Documents\\GitHub\\SAD_PBI_2\\implementacao\\Fase2\\Output\\csv\\" + fileName + ".csv";
        String destinationFilePath = "C:\\Users\\diogo\\Documents\\GitHub\\SAD_PBI_2\\implementacao\\Fase2\\Output\\csv\\" + fileName + "New.csv";
        File source = new File(sourceFilePath);
        try {
            Scanner csvScanner = new Scanner(source);
            PrintWriter csvWritter = new PrintWriter(destinationFilePath);
            while (csvScanner.hasNextLine()) {
                String line = csvScanner.nextLine();
                String formatedLine = line.replace(" ", "").replace("\"", "");
                csvWritter.println(formatedLine);
                System.out.println("Read line: " + line);
                System.out.println("New line: " + formatedLine);
            }
            csvScanner.close();
            csvWritter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error opening file.");
        }
    }

    private void uniCSVtoARFF() throws IOException {
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
            } catch (IOException e) {
                System.out.println("Error with file.");
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void generatingTransformations() {

        GeneratingTransformations instance;

        try {
            // Kettle Environment must always be initialized first when using PDI
            // It bootstraps the PDI engine by loading settings, appropriate plugins
            // etc.
            KettleEnvironment.init(false);

            // Create an instance of this demo class for convenience
            instance = new GeneratingTransformations();

            // generates a simple transformation, returning the TransMeta object describing it

            // get the xml of the definition and save it to a file for inspection in spoon
            Scanner input = new Scanner(System.in);
            System.out.print("Insert the name of the ktr: ");
            String fileName = input.nextLine();
            System.out.println("Read " + fileName);
            input = new Scanner(System.in);
            System.out.print("Is the OS Windows? ");
            String reply = input.next();
            if (reply.equals("y") || reply.equals("yes")) reply = reply.replace("\\", "/");
            //String xml = transMeta.getXML();
            File file = new File(fileName);
            TransMeta transMeta = new TransMeta(fileName);
            //FileUtils.writeStringToFile( file, xml, "UTF-8" );
            instance.generateTransformation(transMeta);

            System.out.println("DONE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public TransMeta generateTransformation(TransMeta transMeta) {
        try {
            System.out.println( "Generating a transformation definition" );

            // create empty transformation definition
            //TransMeta transMeta = new TransMeta();
            //transMeta.setName( "Generated Demo Transformation" );
            Trans transformation = new Trans(transMeta);
            transformation.execute(null);
            transformation.waitUntilFinished();

            // The plug-in registry is used to determine the plug-in ID of each step used
            // PluginRegistry registry = PluginRegistry.getInstance();

            // ------------------------------------------------------------------------------------
            // Create Row Generator Step and put it into the transformation
            // ------------------------------------------------------------------------------------
            //System.out.println( "- Adding Row Generator Step" );

            // Create Step Definition and determine step ID
            /*RowGeneratorMeta rowGeneratorMeta = new RowGeneratorMeta();
            String rowGeneratorPluginId = registry.getPluginId( StepPluginType.class, rowGeneratorMeta );

            // Step it is configured to generate 5 rows with 2 fields
            // field_1: "Hello World" (PDI Type: String)
            // field_2: 100 (PDI Type: Integer)

            rowGeneratorMeta.setRowLimit( "5" );

            rowGeneratorMeta.allocate( 2 );
            rowGeneratorMeta.setFieldName( new String[] { "field_1", "field_2" } );
            rowGeneratorMeta.setFieldType( new String[] { "String", "Integer" } );
            rowGeneratorMeta.setValue( new String[] { "Hello World", "100" } );

            StepMeta rowGeneratorStepMeta = new StepMeta( rowGeneratorPluginId, "Generate Some Rows", rowGeneratorMeta );

            // make sure the step appears on the canvas and is properly placed in spoon
            rowGeneratorStepMeta.setDraw( true );
            rowGeneratorStepMeta.setLocation( 100, 100 );

            // include step in transformation
            transMeta.addStep( rowGeneratorStepMeta );

            // ------------------------------------------------------------------------------------
            // Create "Add Sequence" Step and connect it the Row Generator
            // ------------------------------------------------------------------------------------
            System.out.println( "- Adding Add Sequence Step" );

            // Create Step Definition
            AddSequenceMeta addSequenceMeta = new AddSequenceMeta();
            String addSequencePluginId = registry.getPluginId( StepPluginType.class, addSequenceMeta );

            // configure counter options
            addSequenceMeta.setDefault();
            addSequenceMeta.setValuename( "counter" );
            addSequenceMeta.setCounterName( "counter_1" );
            addSequenceMeta.setStartAt( 1 );
            addSequenceMeta.setMaxValue( Long.MAX_VALUE );
            addSequenceMeta.setIncrementBy( 1 );

            StepMeta addSequenceStepMeta = new StepMeta( addSequencePluginId, "Add Counter Field", addSequenceMeta );

            // make sure the step appears on the canvas and is properly placed in spoon
            addSequenceStepMeta.setDraw( true );
            addSequenceStepMeta.setLocation( 300, 100 );

            // include step in transformation
            transMeta.addStep( addSequenceStepMeta );

            // connect row generator to add sequence step
            transMeta.addTransHop( new TransHopMeta( rowGeneratorStepMeta, addSequenceStepMeta ) );

            // ------------------------------------------------------------------------------------
            // Add a "Dummy" Step and connect it the previous step
            // ------------------------------------------------------------------------------------
            System.out.println( "- Adding Dummy Step" );
            // Create Step Definition
            DummyTransMeta dummyMeta = new DummyTransMeta();
            String dummyPluginId = registry.getPluginId( StepPluginType.class, dummyMeta );

            StepMeta dummyStepMeta = new StepMeta( dummyPluginId, "Dummy", dummyMeta );

            // make sure the step appears alright in spoon
            dummyStepMeta.setDraw( true );
            dummyStepMeta.setLocation( 500, 100 );

            // include step in transformation
            transMeta.addStep( dummyStepMeta );

            // connect row generator to add sequence step
            transMeta.addTransHop( new TransHopMeta( addSequenceStepMeta, dummyStepMeta ) );

            return transMeta;*/

        } catch ( Exception e ) {
            // something went wrong, just log and return
            e.printStackTrace();
        }
        return null;
    }
}

