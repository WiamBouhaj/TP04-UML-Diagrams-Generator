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

public class Exemple3 {

	public Exemple3() {
	}
	    public static void main(String[] args) {
	    	    // Répertoire de test : chemin racine où se trouvent les classes compilées
	    	    //File testDir = new File("C:\\Users\\WIAM\\git\\repository\\TP04-UML-Diagrams-Generator\\bin");
	    	    //File testDir = new File("C:\\JAVA-MQL\\p04-xml-parsors\\bin");
	     	    File testDir = new File("C:\\JAVA-MQL\\p03_reflection_and_annotations\\bin");
	    	    
	    	    if (!testDir.exists() || !testDir.isDirectory()) {
	    	        System.out.println("Le répertoire de test n'existe pas ou n'est pas valide : " + testDir.getPath());
	    	        return;
	    	    }

	    	    System.
	    	    out.println("==== Analyse du Projet ====");

	    	    // Initialisation du parser de projet
	    	    ProjectParser projectExplorer = new ProjectParser(testDir); // Passez le chemin racine ici
	    	    ProjectModel projectModel = projectExplorer.parse(testDir);

	    	    if (projectModel != null) {
	    	        System.out.println("Projet analysé : " + projectModel.getName());

	    	        // Parcourir les packages
	    	        projectModel.getPackages().forEach(pkg -> {
	    	            System.out.println("Package : " + pkg.getName());

	    	            // Parcourir les classes dans chaque package
	    	            pkg.getClasses().forEach(cls -> {
	    	                System.out.println("\tClasse : " + cls.getName());
	    	                System.out.println("\t\tHéritage : " + (cls.getSuperClass() != null ? cls.getSuperClass().getName() : "Aucun"));
	    	                System.out.println("\t\tInterfaces : " + (cls.getInterfaces().isEmpty() ? "Aucune" : cls.getInterfaces()));

	    	                // Champs (fields)
	    	                System.out.println("\t\tChamps : ");
	    	                cls.getFields().forEach(field -> {
	    	                    System.out.println("\t\t\t- Champ : " + field.getName() + 
	    	                                       ", Type : " + field.getType() +
	    	                                       ", Visibilité : " + field.getVisibility());
	    	                });

	    	                // Méthodes
	    	                System.out.println("\t\tMéthodes : ");
	    	                cls.getMethods().forEach(method -> {
	    	                    System.out.println("\t\t\t- Méthode : " + method.getName() + 
	    	                                       ", Retour : " + method.getReturnType() +
	    	                                       ", Visibilité : " + method.getVisibility());
	    	                });

	    	                // Relations
	    	                System.out.println("\t\tRelations : ");
	    	                cls.getRelations().forEach(relation -> {
	    	                    System.out.println("\t\t\t- Relation : " + relation.getType() + 
	    	                                       " avec " + relation.getTarget());
	    	                });
	    	            });
	    	        });
	    	    } else {
	    	        System.out.println("ProjectParser n'a pas pu analyser le projet.");
	    	    }
	    	}
	    }
