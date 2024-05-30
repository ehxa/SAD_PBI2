package SADPBI2;

import org.w3c.dom.Element;
import weka.associations.Apriori;
import weka.associations.AssociationRules;
import weka.associations.AssociationRule;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

import java.io.File;
import java.util.*;

public class RunWeka {
    public List<List<String>> apriori(int rules, double confidence, double support) {
        List<List<String>> tasks = new ArrayList<>();
        try {
            ArffLoader loader = new ArffLoader();
            UtilsXML.makeDoc();
            Element title = UtilsXML.addRoot("SADPBI2");
            for (int i = 1; i < 5; i++) {
                List<String> task = new ArrayList<>();
                String path = "C:\\Users\\diogo\\Documents\\GitHub\\SAD_PBI_2\\implementacao\\Fase2\\Output\\arff\\TASKDATA" + i + ".arff";
                loader.setSource(new File(path));
                Instances data = loader.getDataSet();
                NumericToNominal convert = new NumericToNominal();
                data = Filter.useFilter(data, convert);
                Apriori apriori = new Apriori();
                apriori.setNumRules(rules);
                apriori.setMinMetric(confidence);
                apriori.setLowerBoundMinSupport(support);
                apriori.buildAssociations(data);
                AssociationRules associations = apriori.getAssociationRules();
                Element subtitle = UtilsXML.addElement("TASKDATA" + i, title);
                for (AssociationRule association : associations.getRules()) {
                    String found = association.toString();
                    task.add(found);
                    Element category = UtilsXML.addElement("Association", subtitle);
                    String premise = found.split("==>")[0].trim();
                    UtilsXML.addNode(category, "Premise", premise);
                    String consequence = found.split("==>")[1].split("<conf:")[0].trim();
                    UtilsXML.addNode(category, "Consequence", consequence);
                    String confidenceOut = found.split("<conf:")[1].split(">")[0].trim().replace("(", "").replace(")", "");
                    UtilsXML.addNode(category, "Confidence", confidenceOut);
                    String lift = found.split("lift:")[1].split("\\)")[0].trim().replace("(", "").replace(")", "");
                    UtilsXML.addNode(category, "Lift", lift);
                    String leverage = found.split("lev:")[1].split("\\)")[0].trim().replace("(", "").replace(")", "");
                    UtilsXML.addNode(category, "Leverage", leverage);
                    String conviction = found.split("conv:")[1].split("\\)")[0].trim().replace("(", "").replace(")", "");
                    UtilsXML.addNode(category, "Conviction", conviction);
                }
                tasks.add(task);
            }
            UtilsXML.makeFile("C:\\Users\\diogo\\Documents\\GitHub\\SAD_PBI_2\\implementacao\\Fase2\\Output\\xml\\Associations.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public static void main(String[] args) {
        List<List<String>> tasks = new ArrayList<>();
        RunWeka runWeka = new RunWeka();
        tasks = runWeka.apriori(20, 0.5, 0.05);
        System.out.println(tasks);
    }
}
