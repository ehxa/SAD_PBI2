public static void main(String[] args) {
    // O código que você quer executar deve estar aqui
    System.out.println("Olá, mundo!"); // Exemplo de código para imprimir "Olá, mundo!"
}
public static void ConvertCsvToArff() throws I0Exception {
    //Load from CSV
    CSVLoader loader = new CSVLoader();
    loader.setSource(new File(sourceFilePath));
    Instances data = loader.getDataSet();
    System.out.println(data.get(0));

    //Save to ARFF
    ArffSaver saver = new ArffSaver();
    saver.setInstances(data);
    saver.setfile(new File(destinationFilePath));
    saver.writeBatch();

}