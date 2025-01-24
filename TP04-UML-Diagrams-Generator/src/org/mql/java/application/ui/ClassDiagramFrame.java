package org.mql.java.application.ui;import javax.swing.*;

import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.FieldModel;
import org.mql.java.application.models.MethodModel;
import org.mql.java.application.models.PackageModel;
import org.mql.java.application.models.ProjectModel;
import org.mql.java.application.parsers.ProjectParser;

import java.awt.*;  
import java.io.File;  
import java.util.ArrayList;  
import java.util.List;  

public class ClassDiagramFrame extends JFrame {  
    private List<ClassModel> classes;  

    public ClassDiagramFrame(List<ClassModel> classes) {  
        this.classes = classes;  
        setTitle("Diagrammes de Classes");  
        setSize(800, 600);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setLocationRelativeTo(null);  
        
        // Créer un JPanel pour le dessin  
        DiagramPanel diagramPanel = new DiagramPanel(classes);  
        
        // Ajouter le JPanel à un JScrollPane  
        JScrollPane scrollPane = new JScrollPane(diagramPanel);  
        add(scrollPane);  
    }  

    private class DiagramPanel extends JPanel {  
        private List<ClassModel> classes;  

        public DiagramPanel(List<ClassModel> classes) {  
            this.classes = classes;  
            setPreferredSize(new Dimension(1000, 1000)); // Ajustez selon les besoins  
        }  

        @Override  
        protected void paintComponent(Graphics g) {  
            super.paintComponent(g);  
            drawClassDiagrams(g);  
        }  

        private void drawClassDiagrams(Graphics g) {  
            int xOffset = 30;  
            int yOffset = 30;  
            int classBoxWidth = 160;  // Réduction de la largeur  
            int classBoxHeight = 40;   // Réduction de la hauteur du titre de classe  
            int separatorHeight = 10;  // Hauteur du séparateur  

            for (ClassModel classModel : classes) {  
                int fieldsHeight = classModel.getFields().size() * 15; // Champs plus petits  
                int methodsHeight = classModel.getMethods().size() * 15; // Méthodes plus petites  

                // Dessiner le fond de la classe  
                g.setColor(Color.LIGHT_GRAY);  
                g.fillRect(xOffset, yOffset, classBoxWidth, classBoxHeight + fieldsHeight + methodsHeight + separatorHeight * 2);  
                g.setColor(Color.BLACK);  
                g.drawRect(xOffset, yOffset, classBoxWidth, classBoxHeight + fieldsHeight + methodsHeight + separatorHeight * 2);  
                
                // Afficher le nom de la classe  
                g.setColor(Color.BLACK);  
                g.setFont(new Font("Arial", Font.BOLD, 12)); // Police en gras et plus petite  
                g.drawString(classModel.getName(), xOffset + 5, yOffset + 15);  

                // Séparateur entre le nom et les champs  
                g.drawLine(xOffset + 5, yOffset + classBoxHeight, xOffset + classBoxWidth - 5, yOffset + classBoxHeight);  
                
                // Afficher les champs  
                yOffset += 20; // Décalage après le nom de la classe  
                g.setFont(new Font("Arial", Font.PLAIN, 10)); // Police normale plus petite  
                for (FieldModel field : classModel.getFields()) {  
                    g.drawString(field.getName() + " : " + field.getType(), xOffset + 5, yOffset);  
                    yOffset += 15;  
                }  

                // Séparateur entre les champs et les méthodes  
                g.drawLine(xOffset + 5, yOffset, xOffset + classBoxWidth - 5, yOffset);  
                yOffset += separatorHeight;  
                
                // Afficher les méthodes  
                for (MethodModel method : classModel.getMethods()) {  
                    g.drawString(method.getName() + "() : " + method.getReturnType(), xOffset + 5, yOffset);  
                    yOffset += 15;  
                }  

                // Décalage pour la prochaine classe  
                xOffset += classBoxWidth + 20; // Décalage horizontal pour la prochaine classe  
                if (xOffset + classBoxWidth > getWidth()) {  
                    xOffset = 30; // Remise à zéro de xOffset  
                    yOffset += classBoxHeight + fieldsHeight + methodsHeight + separatorHeight * 2 + 20; // Décalage vertical pour la prochaine ligne  
                }  
            }  
        }  
    }  

    public static void main(String[] args) {  
        //File testDir = new File("C:\\Users\\WIAM\\git\\repository\\TP04-UML-Diagrams-Generator\\bin");  
 	    File testDir = new File("C:\\JAVA-MQL\\p03_reflection_and_annotations\\bin");
        if (!testDir.exists() || !testDir.isDirectory()) {  
            System.out.println("Le répertoire de test n'existe pas ou n'est pas valide : " + testDir.getPath());  
            return;  
        }  

        System.out.println("==== Analyse du Projet ====");  
        
        ProjectParser projectExplorer = new ProjectParser(testDir);  
        ProjectModel projectModel = projectExplorer.parse(testDir);  

        if (projectModel != null) {  
            System.out.println("Projet analysé : " + projectModel.getName());  

            List<ClassModel> classes = new ArrayList<>();  
            for (PackageModel pkg : projectModel.getPackages()) {  
                pkg.getClasses().forEach(classes::add);  
            }  

            ClassDiagramFrame frame = new ClassDiagramFrame(classes);  
            frame.setVisible(true);  
        } else {  
            System.out.println("Le projet n'a pas pu être analysé.");  
        }  
    }  
}