package org.mql.java.test.classes;


import org.mql.java.application.models.*;
import org.mql.java.application.parsers.*;

import java.io.File;
import java.util.List;

public class RelationTest {

    public static void main(String[] args) {
        // Chemin vers le projet à analyser
        String projectPath = "C:\\Users\\WIAM\\git\\repository\\TP04-UML-Diagrams-Generator\\bin"; // Remplacez par le chemin de votre projet

        // Charger les classes du projet
        ProjectParser projectParser = new ProjectParser(projectPath);
        ProjectModel project = projectParser.parse("TP04-UML-Diagrams-Generator");  // Passez le nom du projet à la place du chemin

        // Obtenez les classes à partir du modèle du projet
        List<ClassModel> classModels = project.getAllClasses(); // Supposons que cette méthode existe dans ProjectModel

        // Vérifier que les classes ont bien été extraites
        System.out.println("Classes détectées :");
        for (ClassModel classModel : classModels) {
            System.out.println("- " + classModel.getName());
        }

        // Analyser les relations entre les classes
        RelationParser relationParser = new RelationParser(classModels);
        List<RelationModel> relations = relationParser.parseRelations(classModels);

        // Afficher les relations détectées
        System.out.println("\nRelations détectées :");
        for (RelationModel relation : relations) {
            System.out.println(
                    relation.getSource().getName() +
                    " --(" + relation.getType() + ")--> " +
                    relation.getTarget().getName()
            );
        }

        // Tester la validité des résultats (facultatif, pour vérification manuelle ou automatisée)
        if (relations.isEmpty()) {
            System.err.println("Aucune relation détectée. Vérifiez votre parser ou les fichiers fournis.");
        }
    }
}
