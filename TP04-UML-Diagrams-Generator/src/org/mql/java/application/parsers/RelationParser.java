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
        // Constructeur par défaut
    }

    public RelationParser(List<ClassModel> classes) {
        this.classes = classes;
    }

    public List<RelationModel> parseRelations(List<ClassModel> classes) {
        List<RelationModel> relations = new Vector<>();

        for (ClassModel source : classes) {
            try {
                Class<?> cls = Class.forName(source.getName());

                // Héritage
                if (cls.getSuperclass() != null) {
                    relations.add(createRelation(source, cls.getSuperclass().getName(), RelationType.INHERITANCE, Cardinality.ONE));
                }

                // Implémentation d'interfaces
                for (Class<?> iface : cls.getInterfaces()) {
                    relations.add(createRelation(source, iface.getName(), RelationType.IMPLEMENTATION, Cardinality.ONE));
                }

                // Agrégation/Composition + Analyse des cardinalités
                for (Field field : cls.getDeclaredFields()) {
                    String fieldType = field.getType().getName();
                    RelationType relationType = Modifier.isFinal(field.getModifiers())
                            ? RelationType.COMPOSITION
                            : RelationType.AGGREGATION;

                    // Calcul de la cardinalité
                    Cardinality cardinality = determineCardinality(field);
                    relations.add(createRelation(source, fieldType, relationType, cardinality));
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return relations;
    }

    /**
     * Crée une relation avec une source, une cible, un type et une cardinalité.
     */
    private RelationModel createRelation(ClassModel source, String targetName, RelationType type, Cardinality cardinality) {
        ClassModel target = findClassByName(targetName); // Recherchez la classe cible dans les modèles analysés
        if (target == null) {
            target = new ClassModel(targetName); // Si non trouvée, créez un modèle temporaire
        }
        return new RelationModel(type, source, target, cardinality);
    }

    /**
     * Recherche une classe dans les modèles par son nom.
     */
    private ClassModel findClassByName(String name) {
        return classes.stream()
                .filter(cls -> cls.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Détermine la cardinalité d'un champ en fonction de son type.
     */
    private Cardinality determineCardinality(Field field) {
        Class<?> fieldType = field.getType();

        // Si le champ est un tableau
        if (fieldType.isArray()) {
            return Cardinality.MANY;
        }

        // Si le champ est une collection (e.g., List, Set)
        if (List.class.isAssignableFrom(fieldType) || Iterable.class.isAssignableFrom(fieldType)) {
            return Cardinality.MANY;
        }

        // Si le champ est optionnel ou null (non obligatoire)
        if (fieldType.getSimpleName().equals("Optional")) {
            return Cardinality.ZERO_OR_ONE;
        }

        // Par défaut, une cardinalité "1"
        return Cardinality.ONE;
    }
}
