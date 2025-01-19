package org.mql.java.test.classes;

import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.RelationModel;
import org.mql.java.application.parsers.RelationParser;

import java.util.List;

public class TestRelationParser {
    public static void main(String[] args) {
        // Create ClassModel instances for each test class
        ClassModel classA = new ClassModel("org.mql.java.application.models.TestClassA");
        ClassModel classB = new ClassModel("org.mql.java.application.models.TestClassB");
        ClassModel classC = new ClassModel("org.mql.java.application.models.TestClassC");

        // Add them to a list
        List<ClassModel> classes = List.of(classA, classB, classC);

        // Initialize RelationParser with the list of classes
        RelationParser relationParser = new RelationParser(classes);

        // Parse relations
        List<RelationModel> relations = relationParser.parseRelations(classes);

        // Print results
        for (RelationModel relation : relations) {
            System.out.println(relation.getSource().getName() + " -> " 
                + relation.getTarget().getName() + " [" + relation.getType() + "]");
        }
    }
}
