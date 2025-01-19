package org.mql.java.test.classes;

import java.io.File;
import java.util.List;

import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.FieldModel;
import org.mql.java.application.models.MethodModel;
import org.mql.java.application.models.PackageModel;
import org.mql.java.application.models.ProjectModel;
import org.mql.java.application.models.RelationModel;
import org.mql.java.application.parsers.ClassParser;
import org.mql.java.application.parsers.PackageExplorer;
import org.mql.java.application.parsers.RelationParser;

public class Examples {

	public Examples() {
	}
	    public static void main(String[] args) {
	        // Répertoire de test
	    	File testDir = new File("C:\\Users\\WIAM\\git\\repository\\TP04-UML-Diagrams-Generator\\bin");

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
	            }
	        }

	        // 2. Test de PackageExplorer
	        System.out.println("\n** Test PackageExplorer **");
	        PackageExplorer explorer = new PackageExplorer();
	        PackageModel packageModel = explorer.parse(testDir);

	        if (packageModel != null) {
	            System.out.println("Package : " + packageModel.getName());
	            for (ClassModel cls : packageModel.getClasses()) {
	                System.out.println("  Classe : " + cls.getName());
	            }
	        }

	     // 3. Test de RelationParser
	        System.out.println("\n** Test RelationParser **");
	        RelationParser relationParser = new RelationParser();
	        List<RelationModel> allRelations = relationParser.parseRelations(packageModel.getClasses()); // Passez toute la liste

	        // Affichage des relations
	        for (ClassModel cls : packageModel.getClasses()) {
	            System.out.println("Classe : " + cls.getName());
	            for (RelationModel relation : cls.getRelations()) {
	                System.out.println("  Relation : " + relation.getType() +
	                                   " -> " + relation.getTarget().getName());
	            }
	        }


	        // 4. Test d'intégration avec ProjectModel
	        System.out.println("\n** Test ProjectModel **");
	        ProjectModel project = new ProjectModel("org.mql.java.application");
	        project.addPackage(packageModel);

	        System.out.println("Projet : " + project.getName());
	        for (PackageModel pkg : project.getPackages()) {
	            System.out.println("Package : " + pkg.getName());
	            for (ClassModel cls : pkg.getClasses()) {
	                System.out.println("  Classe : " + cls.getName());
	            }
	        }
	    }
}