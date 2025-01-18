package org.mql.java.application.parsers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.mql.java.application.enumerations.RelationType;
import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.RelationModel;

public class RelationParser {
    private final List<RelationModel> relations = new ArrayList<>();

    public List<RelationModel> parse(ClassModel classModel, List<ClassModel> allClasses) {
        // Analyse des relations d'héritage et d'implémentation
        parseInheritanceAndImplementation(classModel, allClasses);

        // Analyse des relations d'association (par les champs)
        parseAssociations(classModel, allClasses);

        return relations;
    }

    /**
     * Analyse les relations d'héritage et d'implémentation.
     */
    private void parseInheritanceAndImplementation(ClassModel classModel, List<ClassModel> allClasses) {
        // Héritage
        ClassModel superClass = classModel.getSuperClass();
        if (superClass != null) {
            relations.add(new RelationModel(RelationType.INHERITANCE, classModel, superClass));
        }

        // Implémentation des interfaces
        for (String interfaceName : classModel.getInterfaces()) {
            ClassModel interfaceModel = findClassModelByName(interfaceName, allClasses);
            if (interfaceModel != null) {
                relations.add(new RelationModel(RelationType.IMPLEMENTATION, classModel, interfaceModel));
            }
        }
    }

    /**
     * Analyse les relations d'association via les champs.
     */
    private void parseAssociations(ClassModel classModel, List<ClassModel> allClasses) {
        for (Field field : getClassFields(classModel)) {
            ClassModel targetClass = findClassModelByName(field.getType().getSimpleName(), allClasses);

            if (targetClass != null) {
                RelationType relationType = isCollection(field) ? RelationType.AGGREGATION : RelationType.ASSOCIATION;
                relations.add(new RelationModel(relationType, classModel, targetClass));
            }
        }
    }

    /**
     * Recherche une classe dans la liste des modèles par son nom.
     */
    private ClassModel findClassModelByName(String className, List<ClassModel> allClasses) {
        for (ClassModel classModel : allClasses) {
            if (classModel.getName().equals(className)) {
                return classModel;
            }
        }
        return null;
    }

    /**
     * Vérifie si un champ est une collection.
     */
    private boolean isCollection(Field field) {
        return List.class.isAssignableFrom(field.getType()) || field.getType().isArray();
    }

    /**
     * Récupère les champs d'une classe en Java.
     */
    private Field[] getClassFields(ClassModel classModel) {
        try {
            Class<?> cls = Class.forName(classModel.getPackageName() + "." + classModel.getName());
            return cls.getDeclaredFields();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new Field[0];
        }
    }
}
