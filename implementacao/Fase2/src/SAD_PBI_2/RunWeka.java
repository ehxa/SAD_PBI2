package SAD_PBI_2;

import weka.associations.Apriori;
import weka.associations.AssociationRules;
import weka.associations.AssociationRule;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.*;
import java.util.*;

import static java.lang.System.out;

public class RunWeka {
    public void apriori(/*int rules, double confidence, double support*/) {
        List<String> list = new ArrayList<>();
        try {
            CSVLoader loader = new CSVLoader();
            for (int i = 1; i < 5; i++){
                String path = "C:\\Users\\diogo\\Documents\\GitHub\\SAD_PBI_2\\implementacao\\Fase2\\Output\\arff\\TASKDATA" + i + ".arff";
                loader.setSource(new File(path));
                Apriori apriori = new Apriori();
                apriori.setNumRules(20);
                apriori.setMinMetric(0.5);
                apriori.setLowerBoundMinSupport(0.05);
                Instances data = loader.getDataSet();
                apriori.buildAssociations(data);
                AssociationRules associations = apriori.getAssociationRules();
                for (AssociationRule association : associations.getRules()){
                    out.println(association+"<br>");
                }
            }
        } catch (Exception e){
            out.println(e);
        }
    }

    public static void main(String[] args) {

    }
}
