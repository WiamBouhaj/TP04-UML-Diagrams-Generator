package org.mql.java.application.ui;
import javax.swing.*;

import org.mql.java.application.enumerations.Visibility;
import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.FieldModel;
import org.mql.java.application.models.MethodModel;
import org.mql.java.application.parsers.ClassParser;

import java.awt.*;
import java.util.List;

public class UMLDiagramPanel extends JPanel {

    private List<ClassModel> classes;

    public UMLDiagramPanel(List<ClassModel> classes) {
        this.classes = classes;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = 50, y = 50;

        // Dessiner chaque classe avec trois compartiments
        for (ClassModel classModel : classes) {
            // Dessiner le rectangle pour la classe
            g.setColor(Color.WHITE);
            g.fillRect(x, y, 250, 150);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, 250, 150);

            // Dessiner le nom de la classe (premier compartiment)
            g.setColor(Color.BLUE);
            g.drawString(classModel.getName(), x + 10, y + 20);

         // Dessiner les attributs
            int fieldY = y + 40;
            for (FieldModel field : classModel.getFields()) {
                // Formater les attributs comme "+Type fieldName"
                g.setColor(Color.BLACK);
                g.drawString((field.getVisibility() == Visibility.PUBLIC ? "+" : "-") + field.getType() + " " + field.getName(), x + 10, fieldY);
                fieldY += 15;
            }

            // Dessiner les méthodes
            int methodY = fieldY + 10;
            for (MethodModel method : classModel.getMethods()) {
                // Formater les méthodes comme "-Type methodName()"
                g.setColor(Color.BLACK);
                g.drawString((method.getVisibility() == Visibility.PUBLIC ? "+" : "-") + " " + method.getReturnType() + " " + method.getName() + "()", x + 10, methodY);
                methodY += 15;
            }
            // Dessiner la ligne pour séparer les compartiments
            g.setColor(Color.BLACK);
            g.drawLine(x, y + 30, x + 250, y + 30); // Séparation du nom des attributs
            g.drawLine(x, y + 100, x + 250, y + 100); // Séparation des attributs et des méthodes

            // Passer à la position de la classe suivante
            y += 220;
        }
    }

    public static void main(String[] args) {
        // Création d'une instance de ClassParser et analyse d'une classe
        ClassParser classParser = new ClassParser();
        ClassModel classModel = classParser.parse(null, "org.mql.java.application.models", "ClassModel");

        // Création de la liste des classes à afficher
        List<ClassModel> classes = List.of(classModel);

        // Créer une fenêtre pour afficher le diagramme
        JFrame frame = new JFrame("Diagramme UML");
        UMLDiagramPanel panel = new UMLDiagramPanel(classes);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.add(panel);
        frame.setVisible(true);
    }
}
