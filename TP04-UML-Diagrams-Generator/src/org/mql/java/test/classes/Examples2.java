package org.mql.java.test.classes;

import java.io.File;
import java.util.List;
import java.util.Vector;

import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.FieldModel;
import org.mql.java.application.models.MethodModel;
import org.mql.java.application.models.PackageModel;
import org.mql.java.application.models.ProjectModel;
import org.mql.java.application.models.RelationModel;
import org.mql.java.application.parsers.ClassParser;
import org.mql.java.application.parsers.PackageExplorer;
import org.mql.java.application.parsers.ProjectParser;
import org.mql.java.application.parsers.RelationParser;

public class Examples2 {

	    public Examples2() {
	    }

	    public static void main(String[] args) {
	        // Répertoire de test
	    	File testDir = new File("C:\\Users\\WIAM\\git\\repository\\TP04-UML-Diagrams-Generator\\bin");
	    	String projectPath = "C:\\Users\\WIAM\\git\\repository\\TP04-UML-Diagrams-Generator\\bin";  
	        
	        // Test complet
	        System.out.println("==== Test Complet ====");

	        // 1. Test de ClassParser
	        System.out.println("** Test ClassParser **");
	        ClassParser classParser = new ClassParser();
	        ClassModel classModel = classParser.parse("org.mql.java.application.models", "ClassModel");

	        if (classModel != null) {
	            System.out.println("Classe : " + classModel.getName());
	            System.out.println("Héritage : " +
	                (classModel.getSuperClass() != null ? classModel.getSuperClass().getName() : "Aucune"));
	            for (FieldModel field : classModel.getFields()) {
	                System.out.println("  Champ : " + field.getName() + ", Type : " + field.getType() +
	                                   ", Visibilité : " + field.getVisibility());
	            }
	            for (MethodModel method : classModel.getMethods()) {
	                System.out.println("  Méthode : " + method.getName() +
	                                   ", Retour : " + method.getReturnType() +
	                                   ", Visibilité : " + method.getVisibility());
	            } String projectName = "MonProjet";  

	            // Étape 1 : Charger le projet  
	            ProjectParser projectParser = new ProjectParser(projectPath);  
	            ProjectModel project = projectParser.parse(projectName);  

	            if (project != null) {  
	                // Étape 2 : Analyser les relations  
	                RelationParser relationParser = new RelationParser();  
	                List<RelationModel> relations = relationParser.parseRelations(project.getAllClasses());  

	             // Étape 3 : Afficher les relations  
	                if (relations.isEmpty()) {  
	                    System.out.println("Aucune relation trouvée.");  
	                } else {  
	                    System.out.println("Relations trouvées : ");  
	                    for (RelationModel relation : relations) {  
	                        System.out.printf("%s %s %s%n",   
	                                          relation.getSource().getName(),   
	                                          relation.getType(),   
	                                          relation.getTarget().getName());  
	                    }  
	                }
	        }  
	    }  
	}
}