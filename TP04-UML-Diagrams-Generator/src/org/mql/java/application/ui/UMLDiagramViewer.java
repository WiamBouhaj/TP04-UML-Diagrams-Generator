package org.mql.java.application.ui;
import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.PackageModel;
import org.mql.java.application.models.ProjectModel;
import org.mql.java.application.models.RelationModel;

import java.awt.*;
import java.util.List;

public class UMLDiagramViewer extends JFrame {
	    private ProjectModel project; // Le modèle du projet (classes, relations, etc.)
	    
	    // Panneaux de l'interface
	    private JPanel contentPanel;
	    private JTree tree; // Pour afficher les classes et les relations
	    
	    public UMLDiagramViewer(String title, ProjectModel projectModel) {
	        super(title);
	        this.project = projectModel;
	        
	        // Configurer la fenêtre principale
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(800, 600);
	        setLocationRelativeTo(null); // Centrer la fenêtre
	        
	        // Initialiser les composants
	        initUI();
	    }
	    
	    private void initUI() {
	        // Créer un panneau pour le contenu
	        contentPanel = new JPanel(new BorderLayout());
	        
	        // Créer un arbre pour afficher les relations des classes (comme un arbre UML)
	        tree = new JTree(createTreeModel());
	        
	        // Ajouter l'arbre dans le panneau
	        contentPanel.add(new JScrollPane(tree), BorderLayout.CENTER);
	        
	        // Ajouter le panneau principal à la fenêtre
	        add(contentPanel);
	    }
	    
	    // Fonction pour créer un modèle d'arbre (JTree) à partir des données du projet
	    private DefaultTreeModel createTreeModel() {
	        // Créez le modèle d'arbre avec les classes et relations
	        // Ce modèle dépendra des classes que vous avez extraites et analysées
	        UMLTreeNode rootNode = new UMLTreeNode("Root");
	        
	        // Vous pouvez ajouter les packages, classes, relations ici en fonction de votre modèle
	        for (PackageModel packageModel : project.getPackages()) {
	            UMLTreeNode packageNode = new UMLTreeNode(packageModel.getName());
	            rootNode.add(packageNode);
	            
	            // Ajouter les classes de chaque package
	            for (ClassModel classModel : packageModel.getClasses()) {
	                UMLTreeNode classNode = new UMLTreeNode(classModel.getName());
	                packageNode.add(classNode);
	                
	                // Ajouter les relations de la classe
	                for (RelationModel relation : classModel.getRelations()) {
	                    UMLTreeNode relationNode = new UMLTreeNode(relation.getType().toString() + " " + relation.getTarget().getName());
	                    classNode.add(relationNode);
	                }
	            }
	        }
	        
	        return new DefaultTreeModel(rootNode);
	    }
	    
	    public static void main(String[] args) {
	        // Créez et affichez l'interface graphique
	        ProjectModel project = new ProjectModel("C:\\JAVA-MQL\\p03_reflection_and_annotations\\bin"); // Remplacez ceci par votre modèle de projet réel
	        UMLDiagramViewer viewer = new UMLDiagramViewer("Visualiseur UML", project);
	        viewer.setVisible(true);
	    }
	}
