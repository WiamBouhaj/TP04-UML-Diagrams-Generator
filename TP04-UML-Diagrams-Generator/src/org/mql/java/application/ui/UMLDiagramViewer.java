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
        // Créer le panneau supérieur pour la recherche (ajusté pour aligner les composants correctement)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout()); // Utiliser BorderLayout pour mieux contrôler les composants

        // Créer un champ de texte pour le chemin du projet
        pathField = new JTextField(30);
        topPanel.add(pathField, BorderLayout.CENTER); // Le champ de texte occupe tout l'espace disponible

        // Créer un bouton "OK"
        loadButton = new JButton("OK");
        topPanel.add(loadButton, BorderLayout.EAST); // Le bouton est aligné à droite

        // Ajouter le panneau en haut de la fenêtre
        add(topPanel, BorderLayout.NORTH);
        // Ajouter un panneau de diagramme vide (initialement)
        diagramPanel = new DiagramPanel(new Vector<>());
        add(diagramPanel, BorderLayout.CENTER);

        // Ajouter un écouteur d'événement au bouton "OK"
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
        // Créez un analyseur de projet
        ProjectParser projectExplorer = new ProjectParser(projectDir);
        ProjectModel projectModel = projectExplorer.parse(projectDir);

        if (projectModel != null) {
            System.out.println("Projet analysé : " + projectModel.getName());

            // Collecter les classes du projet
            Vector<ClassModel> classes = new Vector<>();
            for (PackageModel pkg : projectModel.getPackages()) {
                pkg.getClasses().forEach(classes::add);
            }

            // Afficher le diagramme de classes
            diagramPanel = new DiagramPanel(classes);
            getContentPane().removeAll(); // Retirer les composants existants
            add(pathField, BorderLayout.NORTH);
            add(loadButton, BorderLayout.NORTH);  // Ajouter le bouton "OK" de nouveau
            add(diagramPanel, BorderLayout.CENTER);
            revalidate(); // Mettre à jour l'interface
            repaint();
        } else {
            diagramPanel = new DiagramPanel(new Vector<>()); // Clear the diagram if project parsing fails
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
