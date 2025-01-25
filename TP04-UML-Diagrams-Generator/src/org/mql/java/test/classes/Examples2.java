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
		        File projectDirectory = new File("C:\\Users\\WIAM\\git\\repository\\TP04-UML-Diagrams-Generator\\bin");
		        //File projectDirectory = new File("C:\\JAVA-MQL\\p04-xml-parsors\\bin");
		       
		        ProjectParser projectExplorer = new ProjectParser(projectDirectory);
		        ProjectModel projectModel = projectExplorer.parse(projectDirectory);

		        if (projectModel != null) {
		            System.out.println("Projet analysé : " + projectModel.getName());
		            projectModel.getPackages().forEach(pkg -> {
		                System.out.println("Package : " + pkg.getName());
		                pkg.getClasses().forEach(cls -> System.out.println("  Classe : " + cls.getName()));
		            });
		        }
		    }
		}