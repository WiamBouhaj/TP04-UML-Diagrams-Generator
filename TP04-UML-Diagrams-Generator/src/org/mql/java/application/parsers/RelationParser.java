package org.mql.java.application.parsers;

import org.mql.java.application.enumerations.Cardinality;
import org.mql.java.application.enumerations.RelationType;
import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.RelationModel;

import java.lang.reflect.*;
import java.util.List;
import java.util.Vector;

public class RelationParser {
    private List<ClassModel> classes;

    public RelationParser() {
    }

    public RelationParser(List<ClassModel> classes) {
        this.classes = classes;
    }

    public List<RelationModel> parseRelations(List<ClassModel> classes) {
        List<RelationModel> relations = new Vector<>();

        for (ClassModel source : classes) {
            try {
                Class<?> cls = Class.forName(source.getName());

                if (cls.getSuperclass() != null) {
                    relations.add(createRelation(source, cls.getSuperclass().getName(), RelationType.INHERITANCE, Cardinality.ONE));
                }

                for (Class<?> iface : cls.getInterfaces()) {
                    relations.add(createRelation(source, iface.getName(), RelationType.IMPLEMENTATION, Cardinality.ONE));
                }

                for (Field field : cls.getDeclaredFields()) {
                    String fieldType = field.getType().getName();
                    RelationType relationType = Modifier.isFinal(field.getModifiers())
                            ? RelationType.COMPOSITION
                            : RelationType.AGGREGATION;

                    Cardinality cardinality = determineCardinality(field);
                    relations.add(createRelation(source, fieldType, relationType, cardinality));
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return relations;
    }

    private RelationModel createRelation(ClassModel source, String targetName, RelationType type, Cardinality cardinality) {
        ClassModel target = findClassByName(targetName); // Recherchez la classe cible dans les modèles analysés
        if (target == null) {
            target = new ClassModel(targetName); // Si non trouvée, créez un modèle temporaire
        }
        return new RelationModel(type, source, target, cardinality);
    }

    private ClassModel findClassByName(String name) {
        return classes.stream()
                .filter(cls -> cls.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private Cardinality determineCardinality(Field field) {
        Class<?> fieldType = field.getType();

        if (fieldType.isArray()) {
            return Cardinality.MANY;
        }

        if (List.class.isAssignableFrom(fieldType) || Iterable.class.isAssignableFrom(fieldType)) {
            return Cardinality.MANY;
        }

        if (fieldType.getSimpleName().equals("Optional")) {
            return Cardinality.ZERO_OR_ONE;
        }

        return Cardinality.ONE;
    }
}
