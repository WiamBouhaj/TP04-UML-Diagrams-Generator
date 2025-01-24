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
import org.mql.java.application.parsers.ProjectParser;
import org.mql.java.application.parsers.RelationParser;

public class Examples {

	public Examples() {
	}
	    public static void main(String[] args) {
	    
	        // Répertoire de test
	    	File projectDirectory = new File("C:\\JAVA-MQL\\p04-xml-parsors\\bin");

	        // Test complet
	        System.out.println("==== Test Complet ====");

	        // 1. Test de ClassParser
	        System.out.println("** Test ClassParser **");
	        ClassParser classParser = new ClassParser(null);
	        ClassModel classModel = classParser.parse(projectDirectory,"C:\\JAVA-MQL\\p04-xml-parsors\\bin\\org\\mql\\java\\xml", "AuthorsParser");

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
	        ProjectParser projectExplorer = new ProjectParser(projectDirectory);
	        ProjectModel projectModel = projectExplorer.parse(projectDirectory);

	        if (projectModel != null) {
	            System.out.println("Projet analysé : " + projectModel.getName());
	            projectModel.getPackages().forEach(pkg -> {
	                System.out.println("Package : " + pkg.getName());
	                pkg.getClasses().forEach(cls -> System.out.println("  Classe : " + cls.getName()));
	            });
    
}}}