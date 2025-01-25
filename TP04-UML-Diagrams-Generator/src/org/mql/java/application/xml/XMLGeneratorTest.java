package org.mql.java.application.xml;

import org.mql.java.application.models.ProjectModel;
import org.mql.java.application.parsers.ClassParser;
import org.mql.java.application.models.PackageModel;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.mql.java.application.models.ClassModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLGeneratorTest {

	    public static void main(String[] args) throws MalformedURLException {
	        // Spécifiez le répertoire contenant les classes compilées du projet
	        String classesPath = "C:\\Users\\WIAM\\git\\repository\\TP04-UML-Diagrams-Generator\\bin"; // Remplacez par votre chemin
	        
	        // Spécifiez le chemin du package à analyser
	        String packagePath = "org.mql.java.application.examples"; // Remplacez par votre package

	        // Spécifiez le fichier XML où vous souhaitez sauvegarder le résultat
	        String outputXMLPath = "C:\\Users\\WIAM\\git\\repository\\TP04-UML-Diagrams-Generator\\src\\resources\\File.xml";
	        	

	        // Initialisation du ClassParser avec un class loader
	        File projectDirectory = new File(classesPath);
	        URL[] urls = { projectDirectory.toURI().toURL() };  // Spécifiez le chemin vers les classes
	        URLClassLoader classLoader = new URLClassLoader(urls);
	        ClassParser classParser = new ClassParser(classLoader);

	        try {
	            // Création du modèle XML pour toutes les classes du package
	            org.w3c.dom.Document xmlDoc = XmlGenerator.createEmptyDocument(); // Crée un document XML vide

	            // Analyse de toutes les classes du package
	            File packageDir = new File(classesPath + "\\" + packagePath.replace('.', '\\'));
	            File[] classFiles = packageDir.listFiles((dir, name) -> name.endsWith(".class"));

	            if (classFiles != null) {
	                for (File classFile : classFiles) {
	                    String className = classFile.getName().replace(".class", "");
	                    ClassModel classModel = classParser.parse(projectDirectory, packagePath, className);

	                    if (classModel != null) {
	                        System.out.println("Classe trouvée : " + classModel.getName());

	                        // Ajouter les données de la classe au document XML
	                        XmlGenerator.addClassToXML(xmlDoc, classModel);
	                    }
	                }

	                // Sauvegarder le fichier XML à l'emplacement spécifié
	                XmlGenerator.saveXMLToFile(xmlDoc, outputXMLPath);
	                System.out.println("XML généré et sauvegardé à : " + outputXMLPath);
	            } else {
	                System.out.println("Aucune classe trouvée dans le package " + packagePath);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}