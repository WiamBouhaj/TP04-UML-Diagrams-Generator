package org.mql.java.application.ui;
import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.PackageModel;
import org.mql.java.application.models.ProjectModel;
import org.mql.java.application.models.RelationModel;
import org.mql.java.application.parsers.ProjectParser;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector; 

		public class UMLDiagramViewer extends JFrame {  
		    public UMLDiagramViewer(Vector<ClassModel> classes) {  
		    	 setTitle("Diagramme de Classe");  
		         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		         DiagramPanel panel = new DiagramPanel(classes);  
		         add(panel);  
		         pack(); // Ajuste automatiquement la taille de la fenêtre  
		         setLocationRelativeTo(null); // Centrer la fenêtre  
		    }  

		    public static void main(String[] args) {  
		        // Spécifiez le répertoire de test  
		        File testDir = new File("C:\\Users\\WIAM\\git\\repository\\TP04-UML-Diagrams-Generator\\bin\\org\\mql\\java\\application\\examples");  
		        
		        if (!testDir.exists() || !testDir.isDirectory()) {  
		            System.out.println("Le répertoire de test n'existe pas ou n'est pas valide : " + testDir.getPath());  
		            return;  
		        }  

		        System.out.println("==== Analyse du Projet ====");  

		        // Créez un analyseur de projet  
		        ProjectParser projectExplorer = new ProjectParser(testDir);  
		        ProjectModel projectModel = projectExplorer.parse(testDir);  

		        if (projectModel != null) {  
		            System.out.println("Projet analysé : " + projectModel.getName());  

		            Vector<ClassModel> classes = new Vector<>();  
		            for (PackageModel pkg : projectModel.getPackages()) {  
		                pkg.getClasses().forEach(classes::add);  
		            }  

		            // Affichez le diagramme de classes  
		            UMLDiagramViewer frame = new UMLDiagramViewer(classes);  
		            frame.setVisible(true);  
		        } else {  
		            System.out.println("Le projet n'a pas pu être analysé.");  
		        }  
		    }  
		}  