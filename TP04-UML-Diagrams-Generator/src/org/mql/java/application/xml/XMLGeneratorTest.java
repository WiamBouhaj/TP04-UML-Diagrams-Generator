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
	        String classesPath = "C:\\Users\\WIAM\\git\\repository\\TP04-UML-Diagrams-Generator\\bin";
	      
	        String packagePath = "org.mql.java.application.examples"; 

	       String outputXMLPath = "resources/File.xml";
	        	
           File projectDirectory = new File(classesPath);
	        URL[] urls = { projectDirectory.toURI().toURL() };  
	        URLClassLoader classLoader = new URLClassLoader(urls);
	        ClassParser classParser = new ClassParser(classLoader);

	        try {
	            org.w3c.dom.Document xmlDoc = XmlGenerator.createEmptyDocument(); 
	            File packageDir = new File(classesPath + "\\" + packagePath.replace('.', '\\'));
	            File[] classFiles = packageDir.listFiles((dir, name) -> name.endsWith(".class"));

	            if (classFiles != null) {
	                for (File classFile : classFiles) {
	                    String className = classFile.getName().replace(".class", "");
	                    ClassModel classModel = classParser.parse(projectDirectory, packagePath, className);

	                    if (classModel != null) {
	                        System.out.println("Classe trouvée : " + classModel.getName());

	                        XmlGenerator.addClassToXML(xmlDoc, classModel);
	                    }
	                }

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