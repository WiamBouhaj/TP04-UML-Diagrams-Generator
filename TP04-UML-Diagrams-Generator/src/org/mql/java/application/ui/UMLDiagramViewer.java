package org.mql.java.application.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Vector;

import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.PackageModel;
import org.mql.java.application.models.ProjectModel;
import org.mql.java.application.parsers.ProjectParser;

public class UMLDiagramViewer extends JFrame {
    private JTextField pathField;
    private JButton loadButton;
    private DiagramPanel diagramPanel;

    public UMLDiagramViewer() {
        setTitle("UML Diagram Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
       
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout()); 
        
        pathField = new JTextField(30);
        topPanel.add(pathField, BorderLayout.CENTER); 
        
        loadButton = new JButton("OK");
        topPanel.add(loadButton, BorderLayout.EAST); 
        
        add(topPanel, BorderLayout.NORTH);
        
        diagramPanel = new DiagramPanel(new Vector<>());
        add(diagramPanel, BorderLayout.CENTER);

        loadButton.addActionListener(e -> {
            String projectPath = pathField.getText().trim();
            if (!projectPath.isEmpty()) {
                File projectDir = new File(projectPath);
                if (projectDir.exists() && projectDir.isDirectory()) {
                    generateUMLDiagram(projectDir);
                } else {
                    JOptionPane.showMessageDialog(this, "Le répertoire spécifié n'est pas valide.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un chemin valide.");
            }
        });
    }

    private void generateUMLDiagram(File projectDir) {
        ProjectParser projectExplorer = new ProjectParser(projectDir);
        ProjectModel projectModel = projectExplorer.parse(projectDir);

        if (projectModel != null) {
            System.out.println("Projet analysé : " + projectModel.getName());

            Vector<ClassModel> classes = new Vector<>();
            for (PackageModel pkg : projectModel.getPackages()) {
                pkg.getClasses().forEach(classes::add);
            }

            diagramPanel = new DiagramPanel(classes);
            getContentPane().removeAll(); 
            add(pathField, BorderLayout.NORTH);
            add(loadButton, BorderLayout.NORTH);  
            add(new JScrollPane(diagramPanel), BorderLayout.CENTER);
            revalidate();
            repaint();
        } else {
            diagramPanel = new DiagramPanel(new Vector<>()); 
            repaint();
            JOptionPane.showMessageDialog(this, "Le projet n'a pas pu être analysé.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UMLDiagramViewer frame = new UMLDiagramViewer();
            frame.setVisible(true);
        });
    }
}
